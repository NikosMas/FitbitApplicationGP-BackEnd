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
import com.grad.services.auth.AccessTokenRequestService;
import com.grad.services.auth.RefreshTokenRequestService;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author nikos_mas
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


	public void dataTypeInsert(ResponseEntity<String> responseData, String collection, String filterCollectionName)
			throws IOException, JsonProcessingException {
		JsonNode responseDataBody = mapper.readTree(responseData.getBody());
		DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

		if (collection.equals(CollectionEnum.ACTIVITIES_LIFETIME.getDescription())) {
			mongoTemplate.insert(dataToInsert, collection);
		} else if (filterCollectionName.equals("user")) {
			DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		} else {
			BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
			mongoTemplate.insert(filteredValue, collection);
		}
	}

	public HttpEntity<String> getEntity(boolean unauthorized) throws JsonProcessingException, IOException {
		HttpHeaders headers = new HttpHeaders();
		
		if (unauthorized == false){
			headers.set("Authorization", "Bearer " + getAccessToken());
		}else if (unauthorized == true){
			headers.set("Authorization", "Bearer " + refreshTokenService.refreshToken());
		}
		
		return new HttpEntity<String>(headers);
	}

	protected String getAccessToken() throws JsonProcessingException, IOException {

		if (accessToken == null)
			accessToken = fitbitTokenService.token();

		return accessToken;
	}
}
