package com.grad.data.req;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grad.FitbitToken;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * insert to MongoDB data received from Fitbit API 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitDataSave {
	
	@Autowired
	private ObjectMapper mapperGet;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FitbitToken fitbitToken;
	
	private static String access_token;


	protected String data_timeInBed(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_timeInBed");
		return rootGet.toString();
	}
	
	protected String data_minutesAsleep(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAsleep");
		return rootGet.toString();
	}
	
	protected String data_minutesAwake(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAwake");
		return rootGet.toString();
	}
	
	protected String data_minutesAfterWakeup(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAfterWakeUp");
		return rootGet.toString();
	}
	
	protected String data_minutesToFallAsleep(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesToFallAsleep");
		return rootGet.toString();
	}
	
	protected String data_efficiency(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_efficiency");
		return rootGet.toString();
	}
	
	protected String data_heart(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(heart, "activities_heart");
		return rootGet.toString();
	}
	
	protected String data_steps(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(steps, "activities_steps");
		return rootGet.toString();
	}
	
	protected String data_floors(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(floors, "activities_floors");
		return rootGet.toString();
	}
	
	protected String data_distance(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(distance, "activities_distance");
		return rootGet.toString();
	}
	
	protected String data_calories(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(calories, "activities_calories");
		return rootGet.toString();
	}
	
	protected String data_frequence(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject frequence = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(frequence, "activities_frequence");
		return rootGet.toString();
	}
	
	protected String data_activities(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject activities = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(activities, "activities_lifetime");
		return rootGet.toString();
	}
	
	protected String data_profile(ResponseEntity<String> data) throws IOException, JsonProcessingException {
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject profile = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(profile, "profile");
		return rootGet.toString();
	}
	
	protected HttpEntity<String> getEntity() {
		HttpHeaders headersGet = new HttpHeaders();
		headersGet.set("Authorization", "Bearer " + getAccessToken());
		return new HttpEntity<String>(headersGet);
	}
	
	protected String getAccessToken() {
		if (access_token == null) {
			access_token = fitbitToken.token();
		}
		return access_token;
	}
}
