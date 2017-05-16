package com.grad.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import javaslang.control.Option;

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
	
	private String code;

	private CollectionEnum(String description) {
		this.code = description;
	}

	public String description() {
		return code;
	}

	private static final Map<String, CollectionEnum> stringToEnum = new HashMap<>();
	
	static {
		Arrays.stream(values()).forEach(description -> stringToEnum.put(description.toString(), description));
	}

	@Override
	@JsonValue
	public String toString() {
		return this.code;
	}

	/**
	 * @param string
	 * @return
	 */
	public static Option<CollectionEnum> fromString(String string) {
		CollectionEnum responseCode = stringToEnum.get(string);

		return Option.of(responseCode);
	}
}
