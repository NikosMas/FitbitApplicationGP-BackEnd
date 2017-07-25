package com.fitbit.grad.services.userData;

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

import com.fitbit.grad.domain.CollectionEnum;
import com.fitbit.grad.domain.CommonDataSample;
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

	@Autowired
	private AccessTokenRequestService fitbitTokenService;

	@Autowired
	private RefreshTokenRequestService refreshTokenService;

	private static String accessToken;

	/**
	 * method that stores the data from API
	 * 
	 * @param responseData
	 * @param collection
	 * @param filterCollectionName
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void dataTypeInsert(ResponseEntity<String> responseData, String collection, String filterCollectionName) throws JsonProcessingException, IOException {
		JsonNode responseDataBody = mapper.readTree(responseData.getBody());
		DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

		if (collection.equals(CollectionEnum.A_LIFETIME.d())) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (filterCollectionName.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
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
	 * finds all data and update the dateTime value to "yyyy-mm" date format
	 * 
	 * @param collection inserted the data
	 * @param newCollection where will be stored the new data
	 */
	private void saveByMonth(String collection, String newCollection) {
		mongoTemplate.findAll(CommonDataSample.class, collection).forEach(v -> {
			v.setDateTime(v.getDateTime().substring(0, 7));
			mongoTemplate.insert(v, newCollection);
		});
	}

	/**
	 * @param unauthorized that declares if refresh token should be used 
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public HttpEntity<String> getEntity(boolean unauthorized) throws JsonProcessingException, IOException {
		HttpHeaders headers = new HttpHeaders();

		if (unauthorized == false) {
			headers.set("Authorization", "Bearer " + getAccessToken());
		} else if (unauthorized == true) {
			headers.set("Authorization", "Bearer " + refreshTokenService.refreshToken());
		}

		return new HttpEntity<String>(headers);
	}

	/**
	 * if accessToken is null the service for new is called
	 * 
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	protected String getAccessToken() throws JsonProcessingException, IOException {

		if (accessToken == null)
			accessToken = fitbitTokenService.token();

		return accessToken;
	}
}
