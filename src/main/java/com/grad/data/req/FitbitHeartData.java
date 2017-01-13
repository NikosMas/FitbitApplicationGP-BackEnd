package com.grad.data.req;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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
public class FitbitHeartData {
	
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";
	private static final String ACTIVITIES_HEART = "activities_heart";
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json"
															,"2016-03-01/2016-05-31.json"
															,"2016-06-01/2016-08-31.json"
															,"2016-09-01/2016-11-30.json");
	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private FitbitDataSave fdata;
	
	public void heart() throws JsonProcessingException, IOException {
		
		for(String temp : months){
			ResponseEntity<String> heart = restTemplateGet.exchange(URI_HEART + temp, HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(heart, ACTIVITIES_HEART);		
		}
	}
}	
