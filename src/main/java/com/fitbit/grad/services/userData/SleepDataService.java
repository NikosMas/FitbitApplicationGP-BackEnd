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
 * Service about requesting to fitbit api for sleep data
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class SleepDataService {

	// filtered field from response
	private static final String EFFICIENCY = "sleep-efficiency";
	private static final String MINUTES_TO_FALL_ASLEEP = "sleep-minutesToFallAsleep";
	private static final String MINUTES_AFTER_WAKE_UP = "sleep-minutesAfterWakeup";
	private static final String MINUTES_AWAKE = "sleep-minutesAwake";
	private static final String MINUTES_ASLEEP = "sleep-minutesAsleep";
	private static final String TIME_IN_BED = "sleep-timeInBed";

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private SaveOperationsService saveOperationsService;

	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private FitbitApiUrlProperties urlsProp;

	public boolean sleep(List<Map<String, String>> dates) {

		String p = calendarService.months(dates).stream().filter(month -> dataRetriever(month) == false).findFirst()
				.orElse(null);

		return (p == null) ? true : false;
	}
	
	private boolean dataRetriever(String month) {

		return (requests(urlsProp.getTimeInBedUrl(), month, CollectionEnum.S_TIME_IN_BED.d(), TIME_IN_BED)
				&& requests(urlsProp.getMinutesAsleepUrl(), month, CollectionEnum.S_MINUTES_ASLEEP.d(), MINUTES_ASLEEP)
				&& requests(urlsProp.getMinutesAwakeUrl(), month, CollectionEnum.S_MINUTES_AWAKE.d(), MINUTES_AWAKE)
				&& requests(urlsProp.getAfterWakeUpUrl(), month, CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d(), MINUTES_AFTER_WAKE_UP)
				&& requests(urlsProp.getEfficiencyUrl(), month, CollectionEnum.S_EFFICIENCY.d(), EFFICIENCY)
				&& requests(urlsProp.getToFallAsleepUrl(), month, CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d(), MINUTES_TO_FALL_ASLEEP)) ? true : false;
	}

	/**
	 * sends a call to the given url & if response is unauthorized -> refresh token
	 * and resends
	 * 
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
