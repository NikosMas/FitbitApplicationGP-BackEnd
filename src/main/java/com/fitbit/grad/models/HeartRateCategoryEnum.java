package com.fitbit.grad.models;

/**
 * Enum class containing heart rate zone names 
 * 
 * @author nikos_mas, alex_kak
 */

public enum HeartRateCategoryEnum {
	
	OUT_OF_RANGE("Out of Range"),
	FAT_BURN("Fat Burn"),
	CARDIO("Cardio"),
	PEAK("Peak");
	
	private String d;

	HeartRateCategoryEnum(String d) {
		this.d = d;
	}

	public String d() {
		return d;
	}

}
