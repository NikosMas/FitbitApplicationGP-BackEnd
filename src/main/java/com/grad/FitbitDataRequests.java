package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.grad.FitbitToken;

@Service
public class FitbitDataRequests {

	private String access_token;
	@SuppressWarnings("deprecation")
	Mongo mongo = new Mongo("localhost", 27017);
	@SuppressWarnings("deprecation")
	DB db = mongo.getDB("fitbit");
	
//	@Autowired
//	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FitbitToken fitbitToken;
	
	@Autowired
	private ObjectMapper mapperGet;
	
	@Autowired
	private RestTemplate restTemplateGet;
	
///////////////////////////////////////////PROFILE//////////////////////////////////////////////////
	public String profile() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/profile.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("profile");
		DBObject profile = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(profile);
		return rootGet.toString();
	}

/////////////////////////////////////TIME IN BED/////////////////////////////////////////////////////	
	public String timeInBedDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_timeInBed");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String timeInBedMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_timeInBed");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String timeInBedJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_timeInBed");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String timeInBedSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_timeInBed");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
/////////////////////////////////////////MINUTES ASLEEP////////////////////////////////////////////////////////////////////	
	public String minutesAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAsleepMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
/////////////////////////////////////////////MINUTES AWAKE////////////////////////////////////////////////////////	
	public String minutesAwakeDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAwake");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAwakeMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAwake");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAwakeJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAwake");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAwakeSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAwake");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
////////////////////////////////////////////////MINUTES AFTER WAKE UP////////////////////////////////////////////////////////
	public String minutesAfterWakeupDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAfterWakeUp");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAfterWakeUp");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAfterWakeUp");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesAfterWakeUp");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
//////////////////////////////////////////////////MINUTES TO FALL ASLEEP//////////////////////////////////////////////////	
	public String minutesToFallAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesToFallAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesToFallAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesToFallAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_minutesToFallAsleep");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
////////////////////////////////////////////////////SLEEP EFFICIENCY////////////////////////////////////////////////	
	public String efficiencyDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_efficiency");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String efficiencyMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_efficiency");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String efficiencyJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_efficiency");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
	public String efficiencySep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("sleep_efficiency");
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(sleep);
		return rootGet.toString();
	}
	
/////////////////////////////////////////HEART RATE////////////////////////////////////////////////	
	public String heartDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_heart");
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(heart);
		return rootGet.toString();
	}
	
	public String heartMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_heart");
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(heart);
		return rootGet.toString();
	}
	
	public String heartJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_heart");
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(heart);
		return rootGet.toString();
	}
	
	public String heartSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_heart");
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(heart);
		return rootGet.toString();
	}
	
///////////////////////////////////////////////STEPS//////////////////////////////////////////////////////////////
	public String stepsDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/steps/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_steps");
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(steps);
		return rootGet.toString();
	}
	
	public String stepsMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/steps/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_steps");
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(steps);
		return rootGet.toString();
	}
	
	public String stepsJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/steps/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_steps");
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(steps);
		return rootGet.toString();
	}
	
	public String stepsSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/steps/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_steps");
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(steps);
		return rootGet.toString();
	}
	
////////////////////////////////////////////FLOORS////////////////////////////////////////////////	
	public String floorsDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/floors/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_floors");
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(floors);
		return rootGet.toString();
	}
	
	public String floorsMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/floors/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_floors");
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(floors);
		return rootGet.toString();
	}
	
	public String floorsJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/floors/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_floors");
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(floors);
		return rootGet.toString();
	}
	
	public String floorsSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/floors/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_floors");
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(floors);
		return rootGet.toString();
	}	
	
////////////////////////////////////////////DISTANCE////////////////////////////////////////////////
	public String distanceDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/distance/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_distance");
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(distance);
		return rootGet.toString();
	}
	
	public String distanceMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/distance/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_distance");
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(distance);
		return rootGet.toString();
	}
	
	public String distanceJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/distance/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_distance");
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(distance);
		return rootGet.toString();
	}
	
	public String distanceSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/distance/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_distance");
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(distance);
		return rootGet.toString();
	}
	
////////////////////////////////////////////CALORIES////////////////////////////////////////////////	
	public String caloriesDec15_Feb16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/calories/date/2015-12-01/2016-02-29.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_calories");
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(calories);
		return rootGet.toString();
	}
	
	public String caloriesMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/calories/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_calories");
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(calories);
		return rootGet.toString();
	}
	
	public String caloriesJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/calories/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_calories");
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(calories);
		return rootGet.toString();
	}
	
	public String caloriesSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/calories/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_calories");
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(calories);
		return rootGet.toString();
	}	
	
///////////////////////////////////////////////TOTAL - LIFETIME///////////////////////////////////////////	
	public String activities() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_lifetime");
		DBObject activities = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(activities);
		return rootGet.toString();
	}	
	
//////////////////////////////////////////////FREQUENCE////////////////////////////////////////////////////	
	public String frequence() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/frequence.json";
		ResponseEntity<String> data = restTemplateGet.exchange(uriGet, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBCollection collection = db.getCollection("activities_frequence");
		DBObject frequence = (DBObject) JSON.parse(rootGet.toString());
		collection.insert(frequence);
		return rootGet.toString();
	}	
	
	private HttpEntity<String> getEntity() {
		HttpHeaders headersGet = new HttpHeaders();
		headersGet.set("Authorization", "Bearer " + getAccessToken());
		return new HttpEntity<String>(headersGet);
	}
	
	private String getAccessToken() {
		if (access_token == null) {
			access_token = fitbitToken.token();
		}
		
		return access_token;
	}
}
