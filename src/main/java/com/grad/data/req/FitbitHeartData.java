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

/**
 * heart-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitHeartData {
	
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";
	private static final String ACTIVITIES_HEART = "activities_heart";
	private static final String HEART_RATE = "heart_rate";

	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json"
															,"2016-03-01/2016-05-31.json"
															,"2016-06-01/2016-08-31.json"
															,"2016-09-01/2016-11-30.json");
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
			ResponseEntity<String> heart = restTemplateGet.exchange(URI_HEART + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			JsonNode dataBody = mapperGet.readTree(heart.getBody());
			DBObject dataToInsert = (DBObject) JSON.parse(dataBody.toString());
			BasicDBList value = ((BasicDBList) dataToInsert.get("activities-heart"));
			mongoTemplate.insert(value, ACTIVITIES_HEART);
			
			JSONObject obj = new JSONObject(heart.getBody());
			JSONArray arr = obj.getJSONArray("activities-heart");
			for (int i = 0; i < arr.length(); i++){
				String peak = arr.getJSONObject(i).getString("value");
				JSONObject obj1 = new JSONObject(peak);
			    JSONArray arr1 = obj1.getJSONArray("heartRateZones");
				for (int j = 0; j < arr1.length(); j++){
					DBObject peak1 =  (DBObject) JSON.parse(arr1.getJSONObject(j).toString());
					peak1.put("date", arr.getJSONObject(i).getString("dateTime"));
					mongoTemplate.insert(peak1, HEART_RATE);
				}
			}
		}
	}
}	
