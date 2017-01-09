package com.grad;

import java.io.IOException;
import org.codehaus.jackson.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FitbitCalls {
	Logger LOGGER = LoggerFactory.getLogger("FITBIT_GRAD_APP");

	public String starting_process_log(){
		LOGGER.info("-> THE DATA RETRIEVING AND SAVING STARTS RIGHT NOW");
		return null;
	}

	public String dataCalls() throws JsonProcessingException, IOException{
		
		new FitbitData().profile();
		new FitbitData().activities();
		new FitbitData().frequence();
		
		new FitbitData().timeInBedDec15_Feb16();
		new FitbitData().timeInBedMar16_May16();
		new FitbitData().timeInBedJun16_Aug16();
		new FitbitData().timeInBedSep16_Nov16();
		
		new FitbitData().minutesAsleepDec15_Feb16();
		new FitbitData().minutesAsleepMar16_May16();
		new FitbitData().minutesAsleepJun16_Aug16();
		new FitbitData().minutesAsleepSep16_Nov16();
		
		new FitbitData().minutesAwakeDec15_Feb16();
		new FitbitData().minutesAwakeMar16_May16();
		new FitbitData().minutesAwakeJun16_Aug16();
		new FitbitData().minutesAwakeSep16_Nov16();
		
		new FitbitData().minutesAfterWakeupDec15_Feb16();
		new FitbitData().minutesAfterWakeupMar16_May16();
		new FitbitData().minutesAfterWakeupJun16_Aug16();
		new FitbitData().minutesAfterWakeupSep16_Nov16();
		
		new FitbitData().minutesToFallAsleepDec15_Feb16();
		new FitbitData().minutesToFallAsleepMar16_May16();
		new FitbitData().minutesToFallAsleepJun16_Aug16();
		new FitbitData().minutesToFallAsleepSep16_Nov16();
		
		new FitbitData().efficiencyDec15_Feb16();
		new FitbitData().efficiencyMar16_May16();
		new FitbitData().efficiencyJun16_Aug16();
		new FitbitData().efficiencySep16_Nov16();
		
		new FitbitData().heartDec15_Feb16();
		new FitbitData().heartMar16_May16();
		new FitbitData().heartJun16_Aug16();
		new FitbitData().heartSep16_Nov16();
		
		new FitbitData().stepsDec15_Feb16();
		new FitbitData().stepsMar16_May16();
		new FitbitData().stepsJun16_Aug16();
		new FitbitData().stepsSep16_Nov16();
		
		new FitbitData().floorsDec15_Feb16();
		new FitbitData().floorsMar16_May16();
		new FitbitData().floorsJun16_Aug16();
		new FitbitData().floorsSep16_Nov16();
		
		new FitbitData().distanceDec15_Feb16();
		new FitbitData().distanceMar16_May16();
		new FitbitData().distanceJun16_Aug16();
		new FitbitData().distanceSep16_Nov16();
		
		new FitbitData().caloriesDec15_Feb16();
		new FitbitData().caloriesMar16_May16();
		new FitbitData().caloriesJun16_Aug16();
		new FitbitData().caloriesSep16_Nov16();
		
		return null;
	}
	
}
