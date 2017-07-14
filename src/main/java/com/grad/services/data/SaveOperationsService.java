package com.grad.services.data;

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
import com.grad.domain.CollectionEnum;
import com.grad.domain.DataSample;
import com.grad.services.auth.AccessTokenRequestService;
import com.grad.services.auth.RefreshTokenRequestService;
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

		if (collection.equals(CollectionEnum.ACTIVITIES_LIFETIME.desc())) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (filterCollectionName.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);

			switch (collection) {
			case "activities_floors":
				saveByMonth(collection, CollectionEnum.FLOORS_MONTHLY.desc());
				break;
			case "activities_steps":
				saveByMonth(collection, CollectionEnum.STEPS_MONTHLY.desc());
				break;
			case "activities_calories":
				saveByMonth(collection, CollectionEnum.CALORIES_MONTHLY.desc());
				break;
			case "sleep_efficiency":
				saveByMonth(collection, CollectionEnum.EFFICIENCY_MONTHLY.desc());
				break;
			case "sleep_minutesToFallAsleep":
				saveByMonth(collection, CollectionEnum.MINUTES_TO_FALL_ASLEEP_MONTHLY.desc());
				break;
			case "sleep_minutesAfterWakeUp":
				saveByMonth(collection, CollectionEnum.MINUTES_AFTER_WAKE_UP_MONTHLY.desc());
				break;
			case "sleep_minutesAwake":
				saveByMonth(collection, CollectionEnum.MINUTES_AWAKE_MONTHLY.desc());
				break;
			case "sleep_minutesAsleep":
				saveByMonth(collection, CollectionEnum.MINUTES_ASLEEP_MONTHLY.desc());
				break;
			case "sleep_timeInBed":
				saveByMonth(collection, CollectionEnum.TIME_IN_BED_MONTHLY.desc());
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
		mongoTemplate.findAll(DataSample.class, collection).forEach(v -> {
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
