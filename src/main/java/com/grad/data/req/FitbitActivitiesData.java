package com.grad.data.req;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * activities-heart-data-request class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitActivitiesData extends FitbitDataSave{

	private static final String URI_STEPS = "https://api.fitbit.com/1/user/-/activities/steps/date/";
	private static final String URI_FLOORS = "https://api.fitbit.com/1/user/-/activities/floors/date/";
	private static final String URI_DISTANCE = "https://api.fitbit.com/1/user/-/activities/distance/date";
	private static final String URI_CALORIES = "https://api.fitbit.com/1/user/-/activities/calories/date/";
	
	private static final String FIRST = "2015-12-01/2016-02-29.json";
	private static final String SECOND = "2016-03-01/2016-05-31.json";
	private static final String THIRD = "2016-06-01/2016-08-31.json";
	private static final String FOURTH = "2016-09-01/2016-11-30.json";

	@Autowired
	private RestTemplate restTemplateGet;

	///////////////////////////////////////////////STEPS//////////////////////////////////////////////////////////////
	public String stepsDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_steps(data);
	}

	public String stepsMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_steps(data);
	}
	
	public String stepsJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_steps(data);
	}
	
	public String stepsSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_STEPS + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_steps(data);
	}
	
////////////////////////////////////////////FLOORS////////////////////////////////////////////////	
	public String floorsDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_floors(data);
	}

	public String floorsMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_floors(data);
	}
	
	public String floorsJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_floors(data);
	}
	
	public String floorsSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_FLOORS + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_floors(data);
	}	
	
////////////////////////////////////////////DISTANCE////////////////////////////////////////////////
	public String distanceDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_distance(data);
	}

	public String distanceMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_distance(data);
	}
	
	public String distanceJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_distance(data);
	}
	
	public String distanceSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_DISTANCE + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_distance(data);
	}
	
////////////////////////////////////////////CALORIES////////////////////////////////////////////////	
	public String caloriesDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_calories(data);
	}

	public String caloriesMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_calories(data);
	}
	
	public String caloriesJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_calories(data);
	}
	
	public String caloriesSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_CALORIES + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_calories(data);
	}	
}
