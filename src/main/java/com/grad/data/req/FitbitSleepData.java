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
 * sleep-data-request class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitSleepData {

	// URI for each data. body part
	private static final String URI_TIME_IN_BED = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/";
	private static final String URI_MINUTES_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/";
	private static final String URI_MINUTES_AWAKE = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/";
	private static final String URI_TO_FALL_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/";
	private static final String URI_AFTER_WAKE_UP = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/";
	private static final String URI_EFFICIENCY = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/";
	// MongoDB collection name
	private static final String SLEEP_EFFICIENCY = "sleep_efficiency";
	private static final String SLEEP_MINUTES_TO_FALL_ASLEEP = "sleep_minutesToFallAsleep";
	private static final String SLEEP_MINUTES_AFTER_WAKE_UP = "sleep_minutesAfterWakeUp";
	private static final String SLEEP_MINUTES_AWAKE = "sleep_minutesAwake";
	private static final String SLEEP_MINUTES_ASLEEP = "sleep_minutesAsleep";
	private static final String SLEEP_TIME_IN_BED = "sleep_timeInBed";
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
	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private FitbitDataSave fdata;

	public void sleep() throws JsonProcessingException, IOException {

		months.stream().forEach(temp -> dataRetriever(temp));
	}

	private void dataRetriever(String temp) {
		try {
			ResponseEntity<String> dataTimeInBed = restTemplateGet.exchange(URI_TIME_IN_BED + temp, HttpMethod.GET,
					fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataTimeInBed, SLEEP_TIME_IN_BED, TIME_IN_BED);
			ResponseEntity<String> dataMinutesAsleep = restTemplateGet.exchange(URI_MINUTES_ASLEEP + temp,
					HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataMinutesAsleep, SLEEP_MINUTES_ASLEEP, MINUTES_ASLEEP);
			ResponseEntity<String> dataMinutesAwake = restTemplateGet.exchange(URI_MINUTES_AWAKE + temp, HttpMethod.GET,
					fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataMinutesAwake, SLEEP_MINUTES_AWAKE, MINUTES_AWAKE);
			ResponseEntity<String> dataAfterWakeup = restTemplateGet.exchange(URI_AFTER_WAKE_UP + temp, HttpMethod.GET,
					fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataAfterWakeup, SLEEP_MINUTES_AFTER_WAKE_UP, MINUTES_AFTER_WAKE_UP);
			ResponseEntity<String> dataToFallAsleep = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + temp,
					HttpMethod.GET, fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataToFallAsleep, SLEEP_MINUTES_TO_FALL_ASLEEP, MINUTES_TO_FALL_ASLEEP);
			ResponseEntity<String> dataEfficiency = restTemplateGet.exchange(URI_EFFICIENCY + temp, HttpMethod.GET,
					fdata.getEntity(), String.class);
			fdata.dataTypeInsert(dataEfficiency, SLEEP_EFFICIENCY, EFFICIENCY);
		} catch (IOException e) {
			System.err.println("something is wrong with the calls or the data insert. Please check it out");
			e.printStackTrace();
		}
	}
}
