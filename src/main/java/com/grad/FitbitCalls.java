package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
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
	private FitbitActivitiesData data_activities;
	
	@Autowired
	private FitbitHeartData data_heart;
	
	@Autowired
	private FitbitOtherData data_other;
	
	@Autowired
	private FitbitSleepData data_sleep;
	
	@Autowired
	private FitbitDataSave fdata;
	
	public void dataCalls() throws JsonProcessingException, IOException{
		
		fdata.collectionsCreate();
		
		data_other.profile();
		data_other.lifetime();
		data_other.frequence();
		
		data_heart.heart();
		data_sleep.sleep();
		data_activities.activities();
	}
}
