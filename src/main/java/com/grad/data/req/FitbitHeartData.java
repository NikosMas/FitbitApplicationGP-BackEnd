package com.grad.data.req;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.mysql.fabric.xmlrpc.base.Data;

/**
 * heart-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitHeartData {
	
	// URI for heart data. body part
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";
	// MongoDB collection name 
	private static final String ACTIVITIES_HEART = "activities_heart";
	// MongoDB collection name focus on heart rate values
	private static final String HEART_RATE = "heart_rate";
	// filtered field from response
	private static final String HEART = "activities-heart";
	// URI for heart data. date part
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json",
															 "2016-03-01/2016-05-31.json",
															 "2016-06-01/2016-08-31.json",
															 "2016-09-01/2016-11-30.json",
															 "2016-12-01/2017-02-28.json");
	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private ObjectMapper mapperGet;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FitbitDataSave fdata;
	
	public void heart() throws JsonProcessingException, IOException, JSONException {
		
		for(String temp : months){
			ResponseEntity<String> heartResponse = restTemplateGet.exchange(URI_HEART + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(heartResponse, ACTIVITIES_HEART, HEART);
			
			JSONObject responseBody = new JSONObject(heartResponse.getBody());
			JSONArray responseDataArray = responseBody.getJSONArray("activities-heart");
			for (int i = 0; i < responseDataArray.length(); i++){
				String valueField = responseDataArray.getJSONObject(i).getString("value");
				JSONObject valueFieldObject = new JSONObject(valueField);
			    JSONArray heartRateZonesArray = valueFieldObject.getJSONArray("heartRateZones");
				for (int j = 0; j < heartRateZonesArray.length(); j++){
					DBObject heartRateZonesValue =  (DBObject) JSON.parse(heartRateZonesArray.getJSONObject(j).toString());
					heartRateZonesValue.put("date", responseDataArray.getJSONObject(i).getString("dateTime"));
					mongoTemplate.insert(heartRateZonesValue, HEART_RATE);
				}
			}
		}
	}
}	
