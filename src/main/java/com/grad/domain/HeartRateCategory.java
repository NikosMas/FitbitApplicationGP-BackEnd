package com.grad.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

import javaslang.control.Option;

/**
 * @author nikosmas
 *
 */
public enum HeartRateCategory {
	
	OUT_OF_RANGE("Out of Range"),
	FAT_BURN("Fat Burn"),
	CARDIO("Cardio"),
	PEAK("Peak");
	
	private String code;

	private HeartRateCategory(String description) {
		this.code = description;
	}

	public String description() {
		return code;
	}

	private static final Map<String, HeartRateCategory> stringToEnum = new HashMap<>();
	
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
	public static Option<HeartRateCategory> fromString(String string) {
		HeartRateCategory responseCode = stringToEnum.get(string);

		return Option.of(responseCode);
	}

}
