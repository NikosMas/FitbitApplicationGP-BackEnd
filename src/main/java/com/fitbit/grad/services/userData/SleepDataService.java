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

	/**
	 * @param dates
	 * @return
	 */
	
	public boolean sleep(List<Map<String, String>> dates) {

		String p = calendarService.months(dates).stream().filter(month -> dataRetriever(month) == false).findFirst()
				.orElse(null);

		return (p == null) ? true : false;
	}

	/**
	 * @param month
	 * @return
	 */
	private boolean dataRetriever(String month) {
		boolean success;
		try {
			ResponseEntity<String> timeInBed = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> minutesAsleep = restTemplateGet.exchange(urlsProp.getMinutesAsleepUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> minutesAwake = restTemplateGet.exchange(urlsProp.getMinutesAwakeUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> afterWakeup = restTemplateGet.exchange(urlsProp.getAfterWakeUpUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> toFallAsleep = restTemplateGet.exchange(urlsProp.getToFallAsleepUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> efficiency = restTemplateGet.exchange(urlsProp.getEfficiencyUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);

			if (timeInBed.getStatusCodeValue() == 401) {
				ResponseEntity<String> timeInBedWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(timeInBedWithRefreshToken,CollectionEnum.S_TIME_IN_BED.d(), TIME_IN_BED);
				success = true;
			} else if (timeInBed.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(timeInBed, CollectionEnum.S_TIME_IN_BED.d(),TIME_IN_BED);
				success = true;
			} else {
				return false;
			}

			if (minutesAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAsleepWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(minutesAsleepWithRefreshToken,CollectionEnum.S_MINUTES_ASLEEP.d(), MINUTES_ASLEEP);
				success = true;
			} else if (minutesAsleep.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(minutesAsleep,CollectionEnum.S_MINUTES_ASLEEP.d(), MINUTES_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (minutesAwake.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAwakeWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(minutesAwakeWithRefreshToken,CollectionEnum.S_MINUTES_AWAKE.d(), MINUTES_AWAKE);
				success = true;
			} else if (minutesAwake.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(minutesAwake, CollectionEnum.S_MINUTES_AWAKE.d(),MINUTES_AWAKE);
				success = true;
			} else {
				return false;
			}

			if (afterWakeup.getStatusCodeValue() == 401) {
				ResponseEntity<String> afterWakeupWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(afterWakeupWithRefreshToken,CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d(), MINUTES_AFTER_WAKE_UP);
				success = true;
			} else if (afterWakeup.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(afterWakeup,CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d(), MINUTES_AFTER_WAKE_UP);
				success = true;
			} else {
				return false;
			}

			if (toFallAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> toFallAsleepWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(toFallAsleepWithRefreshToken,CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d(), MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else if (toFallAsleep.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(toFallAsleep,CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d(), MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (efficiency.getStatusCodeValue() == 401) {
				ResponseEntity<String> efficiencyWithRefreshToken = restTemplateGet.exchange(urlsProp.getTimeInBedUrl() + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(efficiencyWithRefreshToken,CollectionEnum.S_EFFICIENCY.d(), EFFICIENCY);
				success = true;
			} else if (efficiency.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(efficiency, CollectionEnum.S_EFFICIENCY.d(),EFFICIENCY);
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
