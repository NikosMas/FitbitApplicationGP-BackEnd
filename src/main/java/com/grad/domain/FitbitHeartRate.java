package com.grad.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * model class about collection 'heart_rate' 
 * 
 * @author nikos_mas, alex_kak
 */

@Document(collection = "heart_rate")
public class FitbitHeartRate {

	private String date;
	private String name;
	private Long minutes;
	private Long caloriesOut;
	private Long max;
	private Long min;

	public FitbitHeartRate() {
		super();
	}

	public FitbitHeartRate(String date, String name, Long minutes, Long caloriesOut, Long max, Long min) {
		super();
		this.date = date;
		this.name = name;
		this.minutes = minutes;
		this.caloriesOut = caloriesOut;
		this.max = max;
		this.min = min;
	}

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

	@Override
	public String toString() {
		return "FitbitHeartRate [date=" + date + ", name=" + name + ", minutes=" + minutes + ", caloriesOut="
				+ caloriesOut + ", max=" + max + ", min=" + min + "]";
	}
}
