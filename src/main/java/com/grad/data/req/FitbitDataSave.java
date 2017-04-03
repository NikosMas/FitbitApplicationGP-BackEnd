package com.grad.data.req;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.grad.auth.FitbitToken;
import com.mongodb.BasicDBList;
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

	// MongoDB collections
	private static final String PROFILE = "profile";
	private static final String ACTIVITIES_LIFETIME = "activities_lifetime";
	private static final String ACTIVITIES_FREQUENCE = "activities_frequence";
	private static final String ACTIVITIES_CALORIES = "activities_calories";
	private static final String ACTIVITIES_DISTANCE = "activities_distance";
	private static final String ACTIVITIES_FLOORS = "activities_floors";
	private static final String ACTIVITIES_STEPS = "activities_steps";
	private static final String ACTIVITIES_HEART = "activities_heart";
	private static final String SLEEP_EFFICIENCY = "sleep_efficiency";
	private static final String SLEEP_MINUTES_TO_FALL_ASLEEP = "sleep_minutesToFallAsleep";
	private static final String SLEEP_MINUTES_AFTER_WAKE_UP = "sleep_minutesAfterWakeUp";
	private static final String SLEEP_MINUTES_AWAKE = "sleep_minutesAwake";
	private static final String SLEEP_MINUTES_ASLEEP = "sleep_minutesAsleep";
	private static final String SLEEP_TIME_IN_BED = "sleep_timeInBed";
	private static final String HEART_RATE = "heart_rate";

	// List of the collections
	private static final List<String> collections = Arrays.asList(PROFILE, ACTIVITIES_LIFETIME, ACTIVITIES_FREQUENCE,
			ACTIVITIES_CALORIES, ACTIVITIES_DISTANCE, ACTIVITIES_FLOORS, ACTIVITIES_STEPS, ACTIVITIES_HEART,
			SLEEP_EFFICIENCY, SLEEP_MINUTES_TO_FALL_ASLEEP, SLEEP_MINUTES_AFTER_WAKE_UP, SLEEP_MINUTES_AWAKE,
			SLEEP_MINUTES_ASLEEP, SLEEP_TIME_IN_BED, HEART_RATE);

	@Autowired
	private ObjectMapper mapperGet;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private FitbitToken fitbitToken;

	private static String access_token;

	public void collectionsCreate() {

		collections.stream().forEach(collectionName -> {

			if (mongoTemplate.collectionExists(collectionName)) {
				mongoTemplate.dropCollection(collectionName);
				mongoTemplate.createCollection(collectionName);
			} else {
				mongoTemplate.createCollection(collectionName);
			}
		});
	}

	public void dataTypeInsert(ResponseEntity<String> responseData, String collection, String filterCollectionName)
			throws IOException, JsonProcessingException {
		JsonNode responseDataBody = mapperGet.readTree(responseData.getBody());
		DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

		if (collection.equals(ACTIVITIES_LIFETIME)) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (filterCollectionName.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		}
	}

	public HttpEntity<String> getEntity() throws JsonProcessingException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + getAccessToken());
		return new HttpEntity<String>(headers);
	}

	protected String getAccessToken() throws JsonProcessingException, IOException {
		if (access_token == null) {
			access_token = fitbitToken.token();
		}
		return access_token;
	}
}
