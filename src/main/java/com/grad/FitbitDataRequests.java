package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.grad.FitbitToken;

/**
 * requests to Fitbit API for user data  
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitDataRequests {

	private static String access_token;
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";
	private static final String URI_PROFILE = "https://api.fitbit.com/1/user/-/profile.json";
	private static final String URI_TIME_IN_BED = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/";
	private static final String URI_MINUTES_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/";
	private static final String URI_MINUTES_AWAKE = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/";
	private static final String URI_TO_FALL_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/";
	private static final String URI_AFTER_WAKE_UP = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/";
	private static final String URI_EFFICIENCY = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/";
	private static final String URI_STEPS = "https://api.fitbit.com/1/user/-/activities/steps/date/";
	private static final String URI_FLOORS = "https://api.fitbit.com/1/user/-/activities/floors/date/";
	private static final String URI_DISTANCE = "https://api.fitbit.com/1/user/-/activities/distance/date";
	private static final String URI_CALORIES = "https://api.fitbit.com/1/user/-/activities/calories/date/";
	private static final String URI_FREQUENCE = "https://api.fitbit.com/1/user/-/activities/frequence.json";
	private static final String URI_LIFETIME = "https://api.fitbit.com/1/user/-/activities.json";

	private static final String FIRST = "2015-12-01/2016-02-29.json";
	private static final String SECOND = "2016-03-01/2016-05-31.json";
	private static final String THIRD = "2016-06-01/2016-08-31.json";
	private static final String FOURTH = "2016-09-01/2016-11-30.json";

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FitbitToken fitbitToken;
	
	@Autowired
	private ObjectMapper mapperGet;
	
	@Autowired
	private RestTemplate restTemplateGet;
	
///////////////////////////////////////////PROFILE//////////////////////////////////////////////////
	public String profile() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_PROFILE, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject profile = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(profile, "profile");
		return rootGet.toString();
	}

/////////////////////////////////////TIME IN BED/////////////////////////////////////////////////////	
	public String timeInBedDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_timeInBed");
		return rootGet.toString();
	}
	
	public String timeInBedMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_timeInBed");
		return rootGet.toString();
	}
	
	public String timeInBedJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_timeInBed");
		return rootGet.toString();
	}
	
	public String timeInBedSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_timeInBed");
		return rootGet.toString();
	}
	
/////////////////////////////////////////MINUTES ASLEEP////////////////////////////////////////////////////////////////////	
	public String minutesAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAsleep");
		return rootGet.toString();
	}
	
	public String minutesAsleepMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAsleep");
		return rootGet.toString();
	}
	
	public String minutesAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAsleep");
		return rootGet.toString();
	}
	
	public String minutesAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAsleep");
		return rootGet.toString();
	}
	
/////////////////////////////////////////////MINUTES AWAKE////////////////////////////////////////////////////////	
	public String minutesAwakeDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAwake");
		return rootGet.toString();
	}
	
	public String minutesAwakeMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAwake");
		return rootGet.toString();
	}
	
	public String minutesAwakeJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAwake");
		return rootGet.toString();
	}
	
	public String minutesAwakeSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAwake");
		return rootGet.toString();
	}
	
////////////////////////////////////////////////MINUTES AFTER WAKE UP////////////////////////////////////////////////////////
	public String minutesAfterWakeupDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAfterWakeUp");
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAfterWakeUp");
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAfterWakeUp");
		return rootGet.toString();
	}
	
	public String minutesAfterWakeupSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesAfterWakeUp");
		return rootGet.toString();
	}
	
//////////////////////////////////////////////////MINUTES TO FALL ASLEEP//////////////////////////////////////////////////	
	public String minutesToFallAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesToFallAsleep");
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesToFallAsleep");
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesToFallAsleep");
		return rootGet.toString();
	}
	
	public String minutesToFallAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_minutesToFallAsleep");
		return rootGet.toString();
	}
	
////////////////////////////////////////////////////SLEEP EFFICIENCY////////////////////////////////////////////////	
	public String efficiencyDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_efficiency");
		return rootGet.toString();
	}
	
	public String efficiencyMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_efficiency");
		return rootGet.toString();
	}
	
	public String efficiencyJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_efficiency");
		return rootGet.toString();
	}
	
	public String efficiencySep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject sleep = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(sleep, "sleep_efficiency");
		return rootGet.toString();
	}
	
/////////////////////////////////////////HEART RATE////////////////////////////////////////////////	
	public String heartDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(heart, "activities_heart");
		return rootGet.toString();
	}
	
	public String heartMar16_May16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-03-01/2016-05-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(heart, "activities_heart");
		return rootGet.toString();
	}
	
	public String heartJun16_Aug16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-06-01/2016-08-31.json";
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(heart, "activities_heart");
		return rootGet.toString();
	}
	
	
	public String heartSep16_Nov16() throws JsonProcessingException, IOException {
		String uriGet = "https://api.fitbit.com/1/user/-/activities/heart/date/2016-09-01/2016-11-30.json";
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject heart = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(heart, "activities_heart");
		return rootGet.toString();
	}
	
///////////////////////////////////////////////STEPS//////////////////////////////////////////////////////////////
	public String stepsDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(steps, "activities_steps");
		return rootGet.toString();
	}
	
	public String stepsMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(steps, "activities_steps");
		return rootGet.toString();
	}
	
	public String stepsJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(steps, "activities_steps");
		return rootGet.toString();
	}
	
	public String stepsSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject steps = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(steps, "activities_steps");
		return rootGet.toString();
	}
	
////////////////////////////////////////////FLOORS////////////////////////////////////////////////	
	public String floorsDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(floors, "activities_floors");
		return rootGet.toString();
	}
	
	public String floorsMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(floors, "activities_floors");
		return rootGet.toString();
	}
	
	public String floorsJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(floors, "activities_floors");
		return rootGet.toString();
	}
	
	public String floorsSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject floors = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(floors, "activities_floors");
		return rootGet.toString();
	}	
	
////////////////////////////////////////////DISTANCE////////////////////////////////////////////////
	public String distanceDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(distance, "activities_distance");
		return rootGet.toString();
	}
	
	public String distanceMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(distance, "activities_distance");
		return rootGet.toString();
	}
	
	public String distanceJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(distance, "activities_distance");
		return rootGet.toString();
	}
	
	public String distanceSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject distance = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(distance, "activities_distance");
		return rootGet.toString();
	}
	
////////////////////////////////////////////CALORIES////////////////////////////////////////////////	
	public String caloriesDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + FIRST, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(calories, "activities_calories");
		return rootGet.toString();
	}
	
	public String caloriesMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + SECOND, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(calories, "activities_calories");
		return rootGet.toString();
	}
	
	public String caloriesJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + THIRD, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(calories, "activities_calories");
		return rootGet.toString();
	}
	
	public String caloriesSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + FOURTH, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject calories = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(calories, "activities_calories");
		return rootGet.toString();
	}	
	
///////////////////////////////////////////////TOTAL - LIFETIME///////////////////////////////////////////	
	public String activities() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_LIFETIME, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject activities = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(activities, "activities_lifetime");
		return rootGet.toString();
	}	
	
//////////////////////////////////////////////FREQUENCE////////////////////////////////////////////////////	
	public String frequence() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FREQUENCE, HttpMethod.GET, getEntity(), String.class);
		JsonNode rootGet = mapperGet.readTree(data.getBody());
		DBObject frequence = (DBObject) JSON.parse(rootGet.toString());
		mongoTemplate.insert(frequence, "activities_frequence");
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
