package com.grad.data.services;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * data-request caller class.
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitDataStoreService {

	private ActivitiesDataService activitiesDataStore;
	private HeartDataService heartDataStore;
	private OtherDataService otherDataStore;
	private SleepDataService sleepDataStore;

	@Autowired
	public FitbitDataStoreService(ActivitiesDataService activitiesDataStore, HeartDataService heartDataStore,
			OtherDataService otherDataStore, SleepDataService sleepDataStore) {

		this.activitiesDataStore = activitiesDataStore;
		this.heartDataStore = heartDataStore;
		this.otherDataStore = otherDataStore;
		this.sleepDataStore = sleepDataStore;
	}

	public void dataCalls() throws JsonProcessingException, IOException, JSONException {

		otherDataStore.profile();
		otherDataStore.lifetime();
		otherDataStore.frequence();

		heartDataStore.heart();

		sleepDataStore.sleep();

		activitiesDataStore.activities();
	}
}
