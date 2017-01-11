package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * data-request caller class. 
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitCalls {
	
	@Autowired
	private FitbitDataRequests fitbit;
	
	public void dataCalls() throws JsonProcessingException, IOException{
		
		fitbit.profile();
		fitbit.activities();
		fitbit.frequence();
		
		fitbit.timeInBedDec15_Feb16();
		fitbit.timeInBedMar16_May16();
		fitbit.timeInBedJun16_Aug16();
		fitbit.timeInBedSep16_Nov16();
		
		fitbit.minutesAsleepDec15_Feb16();
		fitbit.minutesAsleepMar16_May16();
		fitbit.minutesAsleepJun16_Aug16();
		fitbit.minutesAsleepSep16_Nov16();
		
		fitbit.minutesAwakeDec15_Feb16();
		fitbit.minutesAwakeMar16_May16();
		fitbit.minutesAwakeJun16_Aug16();
		fitbit.minutesAwakeSep16_Nov16();
		
		fitbit.minutesAfterWakeupDec15_Feb16();
		fitbit.minutesAfterWakeupMar16_May16();
		fitbit.minutesAfterWakeupJun16_Aug16();
		fitbit.minutesAfterWakeupSep16_Nov16();
		
		fitbit.minutesToFallAsleepDec15_Feb16();
		fitbit.minutesToFallAsleepMar16_May16();
		fitbit.minutesToFallAsleepJun16_Aug16();
		fitbit.minutesToFallAsleepSep16_Nov16();
		
		fitbit.efficiencyDec15_Feb16();
		fitbit.efficiencyMar16_May16();
		fitbit.efficiencyJun16_Aug16();
		fitbit.efficiencySep16_Nov16();
		
		fitbit.heartDec15_Feb16();
		fitbit.heartMar16_May16();
		fitbit.heartJun16_Aug16();
		fitbit.heartSep16_Nov16();
		
		fitbit.stepsDec15_Feb16();
		fitbit.stepsMar16_May16();
		fitbit.stepsJun16_Aug16();
		fitbit.stepsSep16_Nov16();
		
		fitbit.floorsDec15_Feb16();
		fitbit.floorsMar16_May16();
		fitbit.floorsJun16_Aug16();
		fitbit.floorsSep16_Nov16();
		
		fitbit.distanceDec15_Feb16();
		fitbit.distanceMar16_May16();
		fitbit.distanceJun16_Aug16();
		fitbit.distanceSep16_Nov16();
		
		fitbit.caloriesDec15_Feb16();
		fitbit.caloriesMar16_May16();
		fitbit.caloriesJun16_Aug16();
		fitbit.caloriesSep16_Nov16();
	}
}
