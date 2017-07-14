package com.grad.services.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.grad.config.FitbitApiUrlProperties;
import com.grad.domain.CollectionEnum;
import com.grad.services.calendar.CalendarService;

/**
 * Service about requesting to fitbit api for activity data
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class ActivitiesDataService {
	
	// response data filter name
	private static final String CALORIES = "activities-calories";
	private static final String DISTANCE = "categories";
	private static final String FLOORS = "activities-floors";
	private static final String STEPS = "activities-steps";

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private FitbitApiUrlProperties urlsProp;

	@Autowired
	private SaveOperationsService saveOperationsService;

	@Autowired
	private CalendarService calendarService;

	/**
	 * @param dates
	 * @return
	 */
	
	public boolean activities(List<Map<String, String>> dates) {

		String p = calendarService.months(dates).stream().filter(pack -> dataRetriever(pack) == false).findFirst()
				.orElse(null);

		return (p == null) ? true : false;
	}

	/**
	 * @param month
	 * @return
	 */
	private boolean dataRetriever(String month) {
		boolean success = false;
		try {
			ResponseEntity<String> steps = restTemplateGet.exchange(urlsProp.getStepsUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> floors = restTemplateGet.exchange(urlsProp.getFloorsUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> calories = restTemplateGet.exchange(urlsProp.getCaloriesUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> distance = restTemplateGet.exchange(urlsProp.getDistanceUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);

			if (steps.getStatusCodeValue() == 401) {
				ResponseEntity<String> stepsWithRefreshToken = restTemplateGet.exchange(urlsProp.getStepsUrl() + month, HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(stepsWithRefreshToken,	CollectionEnum.ACTIVITIES_STEPS.desc(), STEPS);
				success = true;
			} else if (steps.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(steps, CollectionEnum.ACTIVITIES_STEPS.desc(), STEPS);
				success = true;
			} else {
				return false;
			}

			if (floors.getStatusCodeValue() == 401) {
				ResponseEntity<String> floorsWithRefreshToken = restTemplateGet.exchange(urlsProp.getFloorsUrl() + month, HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(floorsWithRefreshToken, CollectionEnum.ACTIVITIES_FLOORS.desc(), FLOORS);
				success = true;
			} else if (floors.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(floors, CollectionEnum.ACTIVITIES_FLOORS.desc(), FLOORS);
				success = true;
			} else {
				return false;
			}

			if (distance.getStatusCodeValue() == 401) {
				ResponseEntity<String> distanceWithRefreshToken = restTemplateGet.exchange(urlsProp.getDistanceUrl() + month, HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(distanceWithRefreshToken, CollectionEnum.ACTIVITIES_DISTANCE.desc(), DISTANCE);
				success = true;
			} else if (distance.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(distance, CollectionEnum.ACTIVITIES_DISTANCE.desc(), DISTANCE);
				success = true;
			} else {
				return false;
			}

			if (calories.getStatusCodeValue() == 401) {
				ResponseEntity<String> caloriesWithRefreshToken = restTemplateGet.exchange(urlsProp.getCaloriesUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(caloriesWithRefreshToken,CollectionEnum.ACTIVITIES_CALORIES.desc(), CALORIES);
				success = true;
			} else if (calories.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(calories, CollectionEnum.ACTIVITIES_CALORIES.desc(),CALORIES);
				success = true;
			} else {
				return false;
			}

		} catch (IOException | RestClientException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
		return success;
	}
}
