package com.grad.data.req;

import java.io.IOException;

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
public class FitbitSleepData extends FitbitDataSave{
	
	private static final String URI_TIME_IN_BED = "https://api.fitbit.com/1/user/-/sleep/timeInBed/date/";
	private static final String URI_MINUTES_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesAsleep/date/";
	private static final String URI_MINUTES_AWAKE = "https://api.fitbit.com/1/user/-/sleep/minutesAwake/date/";
	private static final String URI_TO_FALL_ASLEEP = "https://api.fitbit.com/1/user/-/sleep/minutesToFallAsleep/date/";
	private static final String URI_AFTER_WAKE_UP = "https://api.fitbit.com/1/user/-/sleep/minutesAfterWakeup/date/";
	private static final String URI_EFFICIENCY = "https://api.fitbit.com/1/user/-/sleep/efficiency/date/";
	
	private static final String FIRST = "2015-12-01/2016-02-29.json";
	private static final String SECOND = "2016-03-01/2016-05-31.json";
	private static final String THIRD = "2016-06-01/2016-08-31.json";
	private static final String FOURTH = "2016-09-01/2016-11-30.json";
	
	@Autowired
	private RestTemplate restTemplateGet;

	
	public String timeInBedDec15_Feb16() throws JsonProcessingException, IOException  {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_timeInBed(data);
	}

	public String timeInBedMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_timeInBed(data);
	}
	
	public String timeInBedJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_timeInBed(data);
	}
	
	public String timeInBedSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TIME_IN_BED + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_timeInBed(data);
	}
	
/////////////////////////////////////////MINUTES ASLEEP////////////////////////////////////////////////////////////////////	
	public String minutesAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAsleep(data);
	}

	public String minutesAsleepMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAsleep(data);
	}
	
	public String minutesAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAsleep(data);
	}
	
	public String minutesAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_ASLEEP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAsleep(data);
	}
	
/////////////////////////////////////////////MINUTES AWAKE////////////////////////////////////////////////////////	
	public String minutesAwakeDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAwake(data);
	}

	public String minutesAwakeMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAwake(data);
	}
	
	public String minutesAwakeJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAwake(data);
	}
	
	public String minutesAwakeSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_MINUTES_AWAKE + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAwake(data);
	}

	////////////////////////////////////////////////MINUTES AFTER WAKE UP////////////////////////////////////////////////////////
	public String minutesAfterWakeupDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAfterWakeup(data);
	}

	public String minutesAfterWakeupMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAfterWakeup(data);
	}
	
	public String minutesAfterWakeupJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAfterWakeup(data);
	}
	
	public String minutesAfterWakeupSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_AFTER_WAKE_UP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_minutesAfterWakeup(data);
	}
	
//////////////////////////////////////////////////MINUTES TO FALL ASLEEP//////////////////////////////////////////////////	
	public String minutesToFallAsleepDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_minutesToFallAsleep(data);
	}

	public String minutesToFallAsleepMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_minutesToFallAsleep(data);
	}
	
	public String minutesToFallAsleepJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_minutesToFallAsleep(data);
	}
	
	public String minutesToFallAsleepSep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_TO_FALL_ASLEEP + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_minutesToFallAsleep(data);
	}
	
////////////////////////////////////////////////////SLEEP EFFICIENCY////////////////////////////////////////////////	
	public String efficiencyDec15_Feb16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + FIRST, HttpMethod.GET, getEntity(), String.class);
		return data_efficiency(data);
	}

	public String efficiencyMar16_May16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + SECOND, HttpMethod.GET, getEntity(), String.class);
		return data_efficiency(data);
	}
	
	public String efficiencyJun16_Aug16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + THIRD, HttpMethod.GET, getEntity(), String.class);
		return data_efficiency(data);
	}
	
	public String efficiencySep16_Nov16() throws JsonProcessingException, IOException {
		ResponseEntity<String> data = restTemplateGet.exchange(URI_EFFICIENCY + FOURTH, HttpMethod.GET, getEntity(), String.class);
		return data_efficiency(data);
	}
	
}
