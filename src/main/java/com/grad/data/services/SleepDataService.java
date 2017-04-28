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
 * @author nikos_mas
 */

@Service
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
	// URI for heart data. date part
	private static final List<String> months = Arrays.asList("2015-12-01/2016-02-29.json", "2016-03-01/2016-05-31.json",
			"2016-06-01/2016-08-31.json", "2016-09-01/2016-11-30.json", "2016-12-01/2017-02-28.json");

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private SaveOperationsService service;

	public boolean sleep() {

		String s = months.stream().filter(month -> dataRetriever(month) == false).findFirst().orElse(null);
		
		if (s == null)
			return true;

		return false;
	}

	private boolean dataRetriever(String month) {
		boolean success;
		try {
			ResponseEntity<String> timeInBed = restTemplateGet.exchange(URI_TIME_IN_BED + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> minutesAsleep = restTemplateGet.exchange(URI_MINUTES_ASLEEP + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> minutesAwake = restTemplateGet.exchange(URI_MINUTES_AWAKE + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> afterWakeup = restTemplateGet.exchange(URI_AFTER_WAKE_UP + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> toFallAsleep = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + month, HttpMethod.GET,
					service.getEntity(false), String.class);
			ResponseEntity<String> efficiency = restTemplateGet.exchange(URI_EFFICIENCY + month, HttpMethod.GET,
					service.getEntity(false), String.class);

			if (timeInBed.getStatusCodeValue() == 401) {
				ResponseEntity<String> timeInBedWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(timeInBedWithRefreshToken, CollectionEnum.SLEEP_TIME_IN_BED.getDescription(),
						TIME_IN_BED);
				success = true;
			} else if (timeInBed.getStatusCodeValue() == 200) {
				service.dataTypeInsert(timeInBed, CollectionEnum.SLEEP_TIME_IN_BED.getDescription(), TIME_IN_BED);
				success = true;
			} else {
				return false;
			}

			if (minutesAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAsleepWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(minutesAsleepWithRefreshToken,
						CollectionEnum.SLEEP_MINUTES_ASLEEP.getDescription(), MINUTES_ASLEEP);
				success = true;
			} else if (minutesAsleep.getStatusCodeValue() == 200) {
				service.dataTypeInsert(minutesAsleep, CollectionEnum.SLEEP_MINUTES_ASLEEP.getDescription(),
						MINUTES_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (minutesAwake.getStatusCodeValue() == 401) {
				ResponseEntity<String> minutesAwakeWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(minutesAwakeWithRefreshToken,
						CollectionEnum.SLEEP_MINUTES_AWAKE.getDescription(), MINUTES_AWAKE);
				success = true;
			} else if (minutesAwake.getStatusCodeValue() == 200) {
				service.dataTypeInsert(minutesAwake, CollectionEnum.SLEEP_MINUTES_AWAKE.getDescription(),
						MINUTES_AWAKE);
				success = true;
			} else {
				return false;
			}

			if (afterWakeup.getStatusCodeValue() == 401) {
				ResponseEntity<String> afterWakeupWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(afterWakeupWithRefreshToken,
						CollectionEnum.SLEEP_MINUTES_AFTER_WAKE_UP.getDescription(), MINUTES_AFTER_WAKE_UP);
				success = true;
			} else if (afterWakeup.getStatusCodeValue() == 200) {
				service.dataTypeInsert(afterWakeup, CollectionEnum.SLEEP_MINUTES_AFTER_WAKE_UP.getDescription(),
						MINUTES_AFTER_WAKE_UP);
				success = true;
			} else {
				return false;
			}

			if (toFallAsleep.getStatusCodeValue() == 401) {
				ResponseEntity<String> toFallAsleepWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(toFallAsleepWithRefreshToken,
						CollectionEnum.SLEEP_MINUTES_TO_FALL_ASLEEP.getDescription(), MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else if (toFallAsleep.getStatusCodeValue() == 200) {
				service.dataTypeInsert(toFallAsleep, CollectionEnum.SLEEP_MINUTES_TO_FALL_ASLEEP.getDescription(),
						MINUTES_TO_FALL_ASLEEP);
				success = true;
			} else {
				return false;
			}

			if (efficiency.getStatusCodeValue() == 401) {
				ResponseEntity<String> efficiencyWithRefreshToken = restTemplateGet.exchange(URI_TIME_IN_BED + month,
						HttpMethod.GET, service.getEntity(true), String.class);
				service.dataTypeInsert(efficiencyWithRefreshToken, CollectionEnum.SLEEP_EFFICIENCY.getDescription(),
						EFFICIENCY);
				success = true;
			} else if (efficiency.getStatusCodeValue() == 200) {
				service.dataTypeInsert(efficiency, CollectionEnum.SLEEP_EFFICIENCY.getDescription(), EFFICIENCY);
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
