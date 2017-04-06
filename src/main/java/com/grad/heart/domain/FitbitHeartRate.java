package com.grad.heart.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author nikos_mas
 */

@Document(collection = "heart_rate")
public class FitbitHeartRate {

	private String date;
	private String name;
	private Long minutes;
	private Long caloriesOut;
	private Long max;
	private Long min;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMinutes() {
		return minutes;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	public Long getCaloriesOut() {
		return caloriesOut;
	}

	public void setCaloriesOut(Long caloriesOut) {
		this.caloriesOut = caloriesOut;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}
}
