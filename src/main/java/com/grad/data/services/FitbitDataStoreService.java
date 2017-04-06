package com.grad.data.services;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author nikos_mas
 */

@Service
public class FitbitDataStoreService {

	private ActivitiesDataService activitiesDataStore;
	private HeartDataService heartDataStore;
	private OtherDataService otherDataStore;
	private SleepDataService sleepDataStore;
	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	
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
		
		LOG.info("Profile, lifetime and frequence data recieved and stored to database");

		heartDataStore.heart();
		
		LOG.info("Heart rate data recieved and stored to database");

		sleepDataStore.sleep();
		
		LOG.info("Sleep data recieved and stored to database");

		activitiesDataStore.activities();
		
		LOG.info("Activities data recieved and stored to database");
	}
}
