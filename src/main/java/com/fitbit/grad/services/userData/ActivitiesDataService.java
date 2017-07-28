package com.fitbit.grad.services.userData;

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

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;

/**
 * Service about requesting to Fitbit api for activity data
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class ActivitiesDataService {

	// filtered field from response
	private static final String CALORIES = "activities-calories";
	private static final String DISTANCE = "activities-distance";
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

	public boolean activities(List<Map<String, String>> dates) {

		String p = calendarService.months(dates).stream().filter(pack -> dataRetriever(pack) == false).findFirst()
				.orElse(null);

		return (p == null) ? true : false;
	}

	private boolean dataRetriever(String month) {

		return (requests(urlsProp.getStepsUrl(), month, CollectionEnum.A_STEPS.d(), STEPS)
				&& requests(urlsProp.getFloorsUrl(), month, CollectionEnum.A_FLOORS.d(), FLOORS)
				&& requests(urlsProp.getDistanceUrl(), month, CollectionEnum.A_DISTANCE.d(), DISTANCE)
				&& requests(urlsProp.getCaloriesUrl(), month, CollectionEnum.A_CALORIES.d(), CALORIES)) ? true : false;
	}

	/**
	 * sends a call to the given url & if response is unauthorized -> refresh token
	 * and resends
	 * 
	 * @param success
	 * @param url
	 * @param month
	 * @param collection
	 * @param fcollection
	 * @return
	 */
	private boolean requests(String url, String month, String collection, String fcollection) {
		try {
			ResponseEntity<String> response = restTemplateGet.exchange(url + month, HttpMethod.GET,	saveOperationsService.getEntity(false), String.class);

			if (response.getStatusCodeValue() == 401) {
				ResponseEntity<String> responseWithRefreshToken = restTemplateGet.exchange(url + month, HttpMethod.GET,	saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(responseWithRefreshToken, collection, fcollection);
				return true;
			} else if (response.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(response, collection, fcollection);
				return true;
			} else {
				return false;
			}

		} catch (IOException | RestClientException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}
}
