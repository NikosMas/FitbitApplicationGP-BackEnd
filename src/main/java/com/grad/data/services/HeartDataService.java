package com.grad.data.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grad.collections.CollectionEnum;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * heart-data-request class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class HeartDataService {

	// URI for heart data. body part
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";
	// filtered field from response
	private static final String HEART = "activities-heart";
	// URI for heart data. date part
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json", "2016-03-01/2016-05-31.json",
			"2016-06-01/2016-08-31.json", "2016-09-01/2016-11-30.json", "2016-12-01/2017-02-28.json");
	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private DataSaveService fdata;

	public void heart() throws JsonProcessingException, IOException, JSONException {

		months.stream().forEach(month -> {
			try {
				JSONArray responseDataArray = response(month);
				for (int i = 0; i < responseDataArray.length(); i++) {
					JSONArray heartRateZonesArray = getValues(responseDataArray, i);
					for (int j = 0; j < heartRateZonesArray.length(); j++) {
						insertValues(responseDataArray, i, heartRateZonesArray, j);
					}
				}
			} catch (JSONException | IOException e) {
				System.err.println(e);
			}
		});
	}

	private void insertValues(JSONArray responseDataArray, int i, JSONArray heartRateZonesArray, int j)
			throws JSONException {
		DBObject heartRateZonesValue = (DBObject) JSON.parse(heartRateZonesArray.getJSONObject(j).toString());
		heartRateZonesValue.put("date", responseDataArray.getJSONObject(i).getString("dateTime"));
		mongoTemplate.insert(heartRateZonesValue, CollectionEnum.HEART_RATE.getDescription());
	}

	private JSONArray getValues(JSONArray responseDataArray, int i) throws JSONException {
		JSONObject valueField = responseDataArray.getJSONObject(i).getJSONObject("value");
		//JSONObject valueFieldObject = new JSONObject(valueField);
		JSONArray heartRateZonesArray = valueField.getJSONArray("heartRateZones");
		return heartRateZonesArray;
	}

	private JSONArray response(String month) throws JsonProcessingException, IOException, JSONException {
		ResponseEntity<String> heartResponse = restTemplateGet.exchange(URI_HEART + month, HttpMethod.GET,
				fdata.getEntity(), String.class);
		fdata.dataTypeInsert(heartResponse, CollectionEnum.ACTIVITIES_HEART.getDescription(), HEART);

		JSONObject responseBody = new JSONObject(heartResponse.getBody());
		JSONArray responseDataArray = responseBody.getJSONArray(HEART);
		return responseDataArray;
	}
}
