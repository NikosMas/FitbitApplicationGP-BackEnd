package com.grad.data.services;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grad.collections.CollectionEnum;

/**
 * @author nikos_mas
 */

@Service
public class OtherDataService {

	// URI for each data. body part
	private static final String URI_PROFILE = "https://api.fitbit.com/1/user/-/profile.json";
	private static final String URI_FREQUENCE = "https://api.fitbit.com/1/user/-/activities/frequence.json";
	private static final String URI_LIFETIME = "https://api.fitbit.com/1/user/-/activities.json";

	private static final String PROFILE_USER = "user";
	private static final String FREQUENCE_CATEGORIES = "categories";

	private DataSaveService dataService;
	
	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	public OtherDataService(DataSaveService dataService) {
		
		this.dataService = dataService;
	}

	public void profile() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_PROFILE, HttpMethod.GET, dataService.getEntity(),String.class);
		dataService.dataTypeInsert(data, CollectionEnum.PROFILE.getDescription(), PROFILE_USER);
	}

	public void lifetime() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_LIFETIME, HttpMethod.GET, dataService.getEntity(),	String.class);
		dataService.dataTypeInsert(data, CollectionEnum.ACTIVITIES_LIFETIME.getDescription(), null);
	}

	public void frequence() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FREQUENCE, HttpMethod.GET, dataService.getEntity(),String.class);
		dataService.dataTypeInsert(data, CollectionEnum.ACTIVITIES_FREQUENCE.getDescription(), FREQUENCE_CATEGORIES);
	}
}
