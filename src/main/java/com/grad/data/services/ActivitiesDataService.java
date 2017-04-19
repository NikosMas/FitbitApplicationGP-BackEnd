package com.grad.data.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.grad.collections.CollectionEnum;

/**
 * activities-data-request class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class ActivitiesDataService {

	// URI for each data. body part
	private static final String URI_STEPS = "https://api.fitbit.com/1/user/-/activities/steps/date/";
	private static final String URI_FLOORS = "https://api.fitbit.com/1/user/-/activities/floors/date/";
	private static final String URI_DISTANCE = "https://api.fitbit.com/1/user/-/activities/distance/date";
	private static final String URI_CALORIES = "https://api.fitbit.com/1/user/-/activities/calories/date/";
	
	// response data filter name
	private static final String CALORIES = "activities-calories";
	private static final String DISTANCE = "activities-distance";
	private static final String FLOORS = "activities-floors";
	private static final String STEPS = "activities-steps";
	
	private DataSaveService dataService;
	// URI for heart data. date part
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json", "2016-03-01/2016-05-31.json", "2016-06-01/2016-08-31.json",
			"2016-09-01/2016-11-30.json", "2016-12-01/2017-02-28.json");
	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	
	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	public ActivitiesDataService(DataSaveService dataService) {
		this.dataService = dataService;
	}

	public void activities() throws JsonProcessingException, IOException {

		months.stream().forEach(month -> dataRetriever(month));
	}

	private void dataRetriever(String month) {
		try {
			ResponseEntity<String> dataSteps = restTemplateGet.exchange(URI_STEPS + month, HttpMethod.GET,dataService.getEntity(), String.class);
			dataService.dataTypeInsert(dataSteps, CollectionEnum.ACTIVITIES_STEPS.getDescription(), STEPS);
			
			ResponseEntity<String> dataFloors = restTemplateGet.exchange(URI_FLOORS + month, HttpMethod.GET,dataService.getEntity(), String.class);
			dataService.dataTypeInsert(dataFloors, CollectionEnum.ACTIVITIES_FLOORS.getDescription(), FLOORS);
			
			// ResponseEntity<String> dataDistance =
			// restTemplateGet.exchange(URI_DISTANCE + temp, HttpMethod.GET,
			// fdata.getEntity(), String.class);
			// fdata.dataTypeInsert(dataDistance, Collections.ACTIVITIES_DISTANCE.getDescription(),
			// DISTANCE);
			ResponseEntity<String> dataCalories = restTemplateGet.exchange(URI_CALORIES + month, HttpMethod.GET,dataService.getEntity(), String.class);
			dataService.dataTypeInsert(dataCalories, CollectionEnum.ACTIVITIES_CALORIES.getDescription(), CALORIES);
			
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}
