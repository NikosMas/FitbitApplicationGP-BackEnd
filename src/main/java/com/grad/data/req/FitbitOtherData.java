package com.grad.data.req;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * profile-lifetime-frequence-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitOtherData {

	private static final String URI_PROFILE = "https://api.fitbit.com/1/user/-/profile.json";
	private static final String URI_FREQUENCE = "https://api.fitbit.com/1/user/-/activities/frequence.json";
	private static final String URI_LIFETIME = "https://api.fitbit.com/1/user/-/activities.json";

	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private FitbitDataSave fdata;
	
///////////////////////////////////////////PROFILE//////////////////////////////////////////////////
	public String profile() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_PROFILE, HttpMethod.GET, fdata.getEntity(), String.class);
		return fdata.data_profile(data);
	}

///////////////////////////////////////////////TOTAL - LIFETIME///////////////////////////////////////////	
	public String activities() throws JsonProcessingException, IOException {
	ResponseEntity<String> data = restTemplateGet.exchange(URI_LIFETIME, HttpMethod.GET, fdata.getEntity(), String.class);
	return fdata.data_activities(data);
	}

//////////////////////////////////////////////FREQUENCE////////////////////////////////////////////////////	
	public String frequence() throws JsonProcessingException, IOException {
	ResponseEntity<String> data = restTemplateGet.exchange(URI_FREQUENCE, HttpMethod.GET, fdata.getEntity(), String.class);
	return fdata.data_frequence(data);
	}

	
}
