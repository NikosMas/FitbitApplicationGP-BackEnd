package com.grad.data.req;

import java.io.IOException;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * heart-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitHeartData extends FitbitDataSave{

	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";

	private static final String FIRST = "2015-12-01/2016-02-29.json";
	private static final String SECOND = "2016-03-01/2016-05-31.json";
	private static final String THIRD = "2016-06-01/2016-08-31.json";
	private static final String FOURTH = "2016-09-01/2016-11-30.json";
	
	@Autowired
	private RestTemplate restTemplateGet;
	
	public String heartDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_heart(data);
	}

	public String heartMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_heart(data);
	}
	
	public String heartJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_heart(data);
	}
	
	public String heartSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_HEART + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_heart(data);
	}
	
}
