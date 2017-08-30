package com.fitbit.grad.services.operations;

import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.services.authRequests.AccessTokenRequestService;
import com.fitbit.grad.services.authRequests.RefreshTokenRequestService;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Service about saving user data received to database
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class SaveOperationsService {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	/**
	 * method that stores the data from API
	 *
	 * @param response
	 * @param collection
	 * @param fcollection
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void dataTypeInsert(ResponseEntity<String> response, String collection, String fcollection)
			throws IOException {
		JsonNode responseDataBody = mapper.readTree(response.getBody());
		DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

		if (collection.equals(CollectionEnum.A_LIFETIME.d())) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (fcollection.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(fcollection);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(fcollection);
			mongoTemplate.insert(filteredValue, collection);

			switch (collection) {
				case "a_floors":
					saveByMonth(collection, CollectionEnum.A_FLOORS_M.d());
					break;
				case "a_distance":
					saveByMonth(collection, CollectionEnum.A_DISTANCE_M.d());
					break;
				case "a_steps":
					saveByMonth(collection, CollectionEnum.A_STEPS_M.d());
					break;
				case "a_calories":
					saveByMonth(collection, CollectionEnum.A_CALORIES_M.d());
					break;
				case "s_efficiency":
					saveByMonth(collection, CollectionEnum.S_EFFICIENCY_M.d());
					break;
				case "s_minutesToFallAsleep":
					saveByMonth(collection, CollectionEnum.S_MINUTES_TO_FALL_ASLEEP_M.d());
					break;
				case "s_minutesAfterWakeUp":
					saveByMonth(collection, CollectionEnum.S_MINUTES_AFTER_WAKE_UP_M.d());
					break;
				case "s_minutesAwake":
					saveByMonth(collection, CollectionEnum.S_MINUTES_AWAKE_M.d());
					break;
				case "s_minutesAsleep":
					saveByMonth(collection, CollectionEnum.S_MINUTES_ASLEEP_M.d());
					break;
				case "s_timeInBed":
					saveByMonth(collection, CollectionEnum.S_TIME_IN_BED_M.d());
					break;
				default:
					break;
			}
		}
	}

	/**
	 * finds all data and update the dateTime value to "YYYY-MM" date format
	 *
	 * @param collection    inserted the data
	 * @param newCollection where will be stored the new data
	 */
	private void saveByMonth(String collection, String newCollection) {
		mongoTemplate.findAll(CommonDataSample.class, collection).forEach(v -> {
			v.setDateTime(v.getDateTime().substring(0, 7));
			mongoTemplate.insert(v, newCollection);
		});
	}

	/**
	 * @param body
	 * @param collection
	 */
	public void dailySave(String body, String collection){
		try {
			JsonNode responseDataBody = mapper.readTree(body);
			DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());
			mongoTemplate.insert(dataToInsert, collection);
		} catch (IOException e) {
			LOG.error("Something went wrong: ", e);
		}
	}
}
