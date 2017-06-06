package com.grad.domain;

/**
 * Enum class containing Mongo collection names 
 * 
 * @author nikos_mas, alex_kak
 */

public enum CollectionEnum {

	PROFILE("profile"),
	ACTIVITIES_LIFETIME("activities_lifetime"),
	ACTIVITIES_FREQUENCE("activities_frequence"),
	ACTIVITIES_CALORIES("activities_calories"),
	ACTIVITIES_DISTANCE("activities_distance"),
	ACTIVITIES_FLOORS("activities_floors"),
	ACTIVITIES_STEPS("activities_steps"),
	ACTIVITIES_HEART("activities_heart"),
	SLEEP_EFFICIENCY("sleep_efficiency"),
	SLEEP_MINUTES_TO_FALL_ASLEEP("sleep_minutesToFallAsleep"),
	SLEEP_MINUTES_AFTER_WAKE_UP("sleep_minutesAfterWakeUp"),
	SLEEP_MINUTES_AWAKE("sleep_minutesAwake"),
	SLEEP_MINUTES_ASLEEP("sleep_minutesAsleep"),
	SLEEP_TIME_IN_BED("sleep_timeInBed"),
	HEART_RATE("heart_rate"),
	
	STEPS_MONTHLY("steps_monthly"),
	FLOORS_MONTHLY("floors_monthly"),
	CALORIES_MONTHLY("calories_monthly"),
	EFFICIENCY_MONTHLY("efficiency_monthly"),
	MINUTES_TO_FALL_ASLEEP_MONTHLY("minutes_to_fall_asleep_monthly"),
	MINUTES_AFTER_WAKE_UP_MONTHLY("minutes_after_wake_up_monthly"),
	MINUTES_AWAKE_MONTHLY("minutes_awake_monthly"),
	MINUTES_ASLEEP_MONTHLY("minutes_asleep_monthly"),
	TIME_IN_BED_MONTHLY("time_in_bed_monthly");
	
	
	private String desc;

	private CollectionEnum(String desc) {
		this.desc = desc;
	}

	public String desc() {
		return desc;
	}

}
