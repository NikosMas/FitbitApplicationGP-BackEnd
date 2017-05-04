package com.grad.services.data;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grad.domain.CollectionEnum;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author nikos_mas
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

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private SaveOperationsService service;

	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private MongoTemplate mongoTemplate;

	public boolean filterHeartRateValues() {
		String s = months.stream().filter(month -> getFilterHeartRate(month) == false).findFirst().orElse(null);

		if (s == null)
			return true;

		return false;
	}

	private boolean getFilterHeartRate(String month) {
		JSONArray responseDataArray = heartCallResponse(month);
		if (responseDataArray != null) {
			for (int rda = 0; rda < responseDataArray.length(); rda++) {
				JSONArray heartRateZonesArray = getValues(responseDataArray, rda);
				for (int hrza = 0; hrza < heartRateZonesArray.length(); hrza++) {
					insertHeartRateValues(responseDataArray, rda, heartRateZonesArray, hrza);
				}
			}
			return true;
		}
		return false;
	}

	private void insertHeartRateValues(JSONArray responseDataArray, int rda, JSONArray heartRateZonesArray, int hrza)
			throws JSONException {
		DBObject heartRateZonesValue = (DBObject) JSON.parse(heartRateZonesArray.getJSONObject(hrza).toString());
		heartRateZonesValue.put("date", responseDataArray.getJSONObject(rda).getString("dateTime"));
		mongoTemplate.insert(heartRateZonesValue, CollectionEnum.HEART_RATE.getDescription());
	}

	private JSONArray getValues(JSONArray responseDataArray, int i) throws JSONException {
		JSONObject valueField = responseDataArray.getJSONObject(i).getJSONObject("value");
		return valueField.getJSONArray("heartRateZones");
	}

	private JSONArray heartCallResponse(String month) {
		try {
			ResponseEntity<String> heart = restTemplateGet.exchange(URI_HEART + month, HttpMethod.GET,
					service.getEntity(false), String.class);

			if (heart.getStatusCodeValue() == 401) {
				ResponseEntity<String> heartWithRefreshToken = restTemplateGet.exchange(URI_HEART + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(heartWithRefreshToken, CollectionEnum.ACTIVITIES_HEART.getDescription(), HEART);
				return new JSONObject(heartWithRefreshToken.getBody()).getJSONArray(HEART);
			} else if (heart.getStatusCodeValue() == 200) {
				service.dataTypeInsert(heart, CollectionEnum.ACTIVITIES_HEART.getDescription(), HEART);
				return new JSONObject(heart.getBody()).getJSONArray(HEART);
			} else {
				return null;
			}
		} catch (JSONException | IOException e) {
			LOG.error(e.toString());
			return null;

		}
	}
}
