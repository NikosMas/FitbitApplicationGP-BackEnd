package com.grad.services.data;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional(propagation = Propagation.REQUIRED)
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
	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	/**
	 * @param responseData
	 * @param collection
	 * @param filterCollectionName
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public void dataTypeInsert(ResponseEntity<String> responseData, String collection, String filterCollectionName) {
		try {
			JsonNode responseDataBody = mapper.readTree(responseData.getBody());
			DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());

			if (collection.equals(CollectionEnum.ACTIVITIES_LIFETIME.description())) {
				mongoTemplate.insert(dataToInsert, collection);
			} else if (filterCollectionName.equals("user")) {
				DBObject filteredValue = (DBObject) dataToInsert.get(filterCollectionName);
				mongoTemplate.insert(filteredValue, collection);
			} else {
				BasicDBList filteredValue = (BasicDBList) dataToInsert.get(filterCollectionName);
				mongoTemplate.insert(filteredValue, collection);
			}

		} catch (IOException e) {
			LOG.error(e.toString());
		}
	}

	/**
	 * @param unauthorized
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
