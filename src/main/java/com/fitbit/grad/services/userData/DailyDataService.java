package com.fitbit.grad.services.userData;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Service
public class DailyDataService {

	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private FitbitApiUrlProperties urlsProp;

	@Autowired
	private SaveOperationsService saveOperationsService;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	
	public boolean storeIntradayData() {

		try {
			callExecution(urlsProp.getStepsUrl(), CollectionEnum.A_STEPS_D.d());
			callExecution(urlsProp.getFloorsUrl(), CollectionEnum.A_FLOORS_D.d());
			callExecution(urlsProp.getDistanceUrl(), CollectionEnum.A_DISTANCE_D.d());
			callExecution(urlsProp.getHeartUrl(), CollectionEnum.A_HEART_D.d());
			
		LOG.info("Today's data til now are successfully received!");	
			
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * @param url
	 * @param collection
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public void callExecution(String url, String collection) throws JsonProcessingException, IOException {
		ResponseEntity<String> response = restTemplateGet.exchange(url + "today/1d/time/00:00/" + LocalTime.now()
		.format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET, saveOperationsService.getEntity(false), String.class);

		if (response.getStatusCodeValue() == 401) {
			ResponseEntity<String> responseWithRefreshToken = restTemplateGet.exchange(url + "today/1d/time/00:00/" + 
			LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET,saveOperationsService.getEntity(true), String.class);
			
			JsonNode responseDataBody = mapper.readTree(responseWithRefreshToken.getBody());
			DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());
			mongoTemplate.insert(dataToInsert, collection);

		} else if (response.getStatusCodeValue() == 200) {
			JsonNode responseDataBody = mapper.readTree(response.getBody());
			DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());
			mongoTemplate.insert(dataToInsert, collection);
		}
	}

}
