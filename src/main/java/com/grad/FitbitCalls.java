package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.data.req.FitbitActivitiesData;
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
	
	public void dataCalls() throws JsonProcessingException, IOException{
		
		data_other.profile();
		data_other.activities();
		data_other.frequence();
		
		data_heart.heartDec15_Feb16();
		data_heart.heartMar16_May16();
		data_heart.heartJun16_Aug16();
		data_heart.heartSep16_Nov16();
		
		data_sleep.timeInBedDec15_Feb16();
		data_sleep.timeInBedMar16_May16();
		data_sleep.timeInBedJun16_Aug16();
		data_sleep.timeInBedSep16_Nov16();
		
		data_sleep.minutesAsleepDec15_Feb16();
		data_sleep.minutesAsleepMar16_May16();
		data_sleep.minutesAsleepJun16_Aug16();
		data_sleep.minutesAsleepSep16_Nov16();
		
		data_sleep.minutesAwakeDec15_Feb16();
		data_sleep.minutesAwakeMar16_May16();
		data_sleep.minutesAwakeJun16_Aug16();
		data_sleep.minutesAwakeSep16_Nov16();
		
		data_sleep.minutesAfterWakeupDec15_Feb16();
		data_sleep.minutesAfterWakeupMar16_May16();
		data_sleep.minutesAfterWakeupJun16_Aug16();
		data_sleep.minutesAfterWakeupSep16_Nov16();
		
		data_sleep.minutesToFallAsleepDec15_Feb16();
		data_sleep.minutesToFallAsleepMar16_May16();
		data_sleep.minutesToFallAsleepJun16_Aug16();
		data_sleep.minutesToFallAsleepSep16_Nov16();
		
		data_sleep.efficiencyDec15_Feb16();
		data_sleep.efficiencyMar16_May16();
		data_sleep.efficiencyJun16_Aug16();
		data_sleep.efficiencySep16_Nov16();
		
		data_activities.stepsDec15_Feb16();
		data_activities.stepsMar16_May16();
		data_activities.stepsJun16_Aug16();
		data_activities.stepsSep16_Nov16();
		
		data_activities.floorsDec15_Feb16();
		data_activities.floorsMar16_May16();
		data_activities.floorsJun16_Aug16();
		data_activities.floorsSep16_Nov16();
		
		data_activities.distanceDec15_Feb16();
		data_activities.distanceMar16_May16();
		data_activities.distanceJun16_Aug16();
		data_activities.distanceSep16_Nov16();
		
		data_activities.caloriesDec15_Feb16();
		data_activities.caloriesMar16_May16();
		data_activities.caloriesJun16_Aug16();
		data_activities.caloriesSep16_Nov16();
	}
}
