package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.data.services.ActivitiesDataService;
import com.grad.data.services.HeartDataService;
import com.grad.data.services.OtherDataService;
import com.grad.data.services.SleepDataService;

/**
 * data-request caller class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitCalls {

	@Autowired
	private ActivitiesDataService activitiesDataStore;

	@Autowired
	private HeartDataService heartDataStore;

	@Autowired
	private OtherDataService otherDataStore;

	@Autowired
	private SleepDataService sleepDataStore;

	public void dataCalls() throws JsonProcessingException, IOException, JSONException {

		otherDataStore.profile();
		otherDataStore.lifetime();
		otherDataStore.frequence();

		heartDataStore.heart();

		sleepDataStore.sleep();

		activitiesDataStore.activities();
	}
}
