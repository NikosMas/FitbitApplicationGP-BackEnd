package com.grad.collections;

/**
 * @author nikos_mas
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
	HEART_RATE("heart_rate");
	
	private String description;
	
	private CollectionEnum(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
	public String getDescription() {
		return description;
	}
}
