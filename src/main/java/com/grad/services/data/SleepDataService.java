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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.grad.domain.CollectionEnum;
import com.grad.services.calendar.CalendarService;

/**
 * @author nikos_mas
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SleepDataService {

	// URI for each data. body part
	private static final String URI_TIME_IN_BED = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/";
	private static final String URI_MINUTES_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/";
	private static final String URI_MINUTES_AWAKE = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/";
	private static final String URI_TO_FALL_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/";
	private static final String URI_AFTER_WAKE_UP = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/";
	private static final String URI_EFFICIENCY = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/";

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
			ResponseEntity<String> timeInBed = restTemplateGet.exchange(URI_TIME_IN_BED + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> minutesAsleep = restTemplateGet.exchange(URI_MINUTES_ASLEEP + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> minutesAwake = restTemplateGet.exchange(URI_MINUTES_AWAKE + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> afterWakeup = restTemplateGet.exchange(URI_AFTER_WAKE_UP + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> toFallAsleep = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);
			ResponseEntity<String> efficiency = restTemplateGet.exchange(URI_EFFICIENCY + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);

			if (timeInBed.getStatusCodeValue() == 401) {
				ResponseEntity<String> timeInBedWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(timeInBedWithRefreshToken,CollectionEnum.SLEEP_TIME_IN_BED.description(), TIME_IN_BED);
				success = true;
			} else if (timeInBed.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(timeInBed, CollectionEnum.SLEEP_TIME_IN_BED.description(),TIME_IN_BED);
				success = true;
			} else {
				return false;
			}

			if (minutesAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAsleepWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(minutesAsleepWithRefreshToken,CollectionEnum.SLEEP_MINUTES_ASLEEP.description(), MINUTES_ASLEEP);
				success = true;
			} else if (minutesAsleep.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(minutesAsleep,CollectionEnum.SLEEP_MINUTES_ASLEEP.description(), MINUTES_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (minutesAwake.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAwakeWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(minutesAwakeWithRefreshToken,CollectionEnum.SLEEP_MINUTES_AWAKE.description(), MINUTES_AWAKE);
				success = true;
			} else if (minutesAwake.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(minutesAwake, CollectionEnum.SLEEP_MINUTES_AWAKE.description(),MINUTES_AWAKE);
				success = true;
			} else {
				return false;
			}

			if (afterWakeup.getStatusCodeValue() == 401) {
				ResponseEntity<String> afterWakeupWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(afterWakeupWithRefreshToken,CollectionEnum.SLEEP_MINUTES_AFTER_WAKE_UP.description(), MINUTES_AFTER_WAKE_UP);
				success = true;
			} else if (afterWakeup.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(afterWakeup,CollectionEnum.SLEEP_MINUTES_AFTER_WAKE_UP.description(), MINUTES_AFTER_WAKE_UP);
				success = true;
			} else {
				return false;
			}

			if (toFallAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> toFallAsleepWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(toFallAsleepWithRefreshToken,CollectionEnum.SLEEP_MINUTES_TO_FALL_ASLEEP.description(), MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else if (toFallAsleep.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(toFallAsleep,CollectionEnum.SLEEP_MINUTES_TO_FALL_ASLEEP.description(), MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (efficiency.getStatusCodeValue() == 401) {
				ResponseEntity<String> efficiencyWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(efficiencyWithRefreshToken,CollectionEnum.SLEEP_EFFICIENCY.description(), EFFICIENCY);
				success = true;
			} else if (efficiency.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(efficiency, CollectionEnum.SLEEP_EFFICIENCY.description(),EFFICIENCY);
				success = true;
			} else {
				return false;
			}

		} catch (IOException | RestClientException e) {
			LOG.error(e.toString());
			return false;
		}
		return success;
	}
}
