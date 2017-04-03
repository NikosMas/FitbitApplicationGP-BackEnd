package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.data.req.FitbitActivitiesData;
import com.grad.data.req.FitbitDataSave;
import com.grad.data.req.FitbitHeartData;
import com.grad.data.req.FitbitOtherData;
import com.grad.data.req.FitbitSleepData;

/**
 * data-request caller class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitCalls {

	@Autowired
	private FitbitActivitiesData activitiesDataStore;

	@Autowired
	private FitbitHeartData heartDataStore;

	@Autowired
	private FitbitOtherData otherDataStore;

	@Autowired
	private FitbitSleepData sleepDataStore;

	@Autowired
	private FitbitDataSave dataStore;

	public void dataCalls() throws JsonProcessingException, IOException, JSONException {

		dataStore.collectionsCreate();

		otherDataStore.profile();
		otherDataStore.lifetime();
		otherDataStore.frequence();

		heartDataStore.heart();

		sleepDataStore.sleep();

		activitiesDataStore.activities();
	}
}
