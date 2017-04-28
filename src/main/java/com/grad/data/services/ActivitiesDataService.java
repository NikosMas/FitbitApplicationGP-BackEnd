package com.grad.data.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

	// URI for heart data. date part
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json", "2016-03-01/2016-05-31.json",
			"2016-06-01/2016-08-31.json", "2016-09-01/2016-11-30.json", "2016-12-01/2017-02-28.json");
	
	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private SaveOperationsService service;

	public boolean activities() {

		String s = months.stream().filter(month -> dataRetriever(month) == false).findFirst().orElse(null);

		if (s == null)
			return true;
		
		return false;
	}

	private boolean dataRetriever(String month) {
		boolean success = false;
		try {
			ResponseEntity<String> steps = restTemplateGet.exchange(URI_STEPS + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> floors = restTemplateGet.exchange(URI_FLOORS + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> calories = restTemplateGet.exchange(URI_CALORIES + month, HttpMethod.GET,
					service.getEntity(false), String.class);

			if (steps.getStatusCodeValue() == 401) {
				ResponseEntity<String> stepsWithRefreshToken = restTemplateGet.exchange(URI_STEPS + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(stepsWithRefreshToken, CollectionEnum.ACTIVITIES_STEPS.getDescription(), STEPS);
				success = true;
			} else if (steps.getStatusCodeValue() == 200) {
				service.dataTypeInsert(steps, CollectionEnum.ACTIVITIES_STEPS.getDescription(), STEPS);
				success = true;
			} else {
				return false;
			}

			if (floors.getStatusCodeValue() == 401) {
				ResponseEntity<String> floorsWithRefreshToken = restTemplateGet.exchange(URI_FLOORS + month,
						HttpMethod.GET, service.getEntity(false), String.class);
				service.dataTypeInsert(floorsWithRefreshToken, CollectionEnum.ACTIVITIES_FLOORS.getDescription(),
						FLOORS);
				success = true;
			} else if (floors.getStatusCodeValue() == 200) {
				service.dataTypeInsert(floors, CollectionEnum.ACTIVITIES_FLOORS.getDescription(), FLOORS);
				success = true;
			} else {
				return false;
			}

			// ResponseEntity<String> dataDistance =
			// restTemplateGet.exchange(URI_DISTANCE + temp, HttpMethod.GET,
			// fdata.getEntity(), String.class);
			// fdata.dataTypeInsert(dataDistance,
			// Collections.ACTIVITIES_DISTANCE.getDescription(),
			// DISTANCE);

			if (calories.getStatusCodeValue() == 401) {
				ResponseEntity<String> caloriesWithRefreshToken = restTemplateGet.exchange(URI_CALORIES + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(caloriesWithRefreshToken, CollectionEnum.ACTIVITIES_CALORIES.getDescription(),
						CALORIES);
				success = true;
			} else if (calories.getStatusCodeValue() == 200) {
				service.dataTypeInsert(calories, CollectionEnum.ACTIVITIES_CALORIES.getDescription(), CALORIES);
				success = true;
			} else {
				return false;
			}

		} catch (IOException e) {
			LOG.error(e.toString());
			return false;
		}
		return success;
	}
}
