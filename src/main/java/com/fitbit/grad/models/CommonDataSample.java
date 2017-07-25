package com.fitbit.grad.models;

/**
 * model class required at saving service  
 * 
 * @author nikos_mas, alex_kak
 */

public class CommonDataSample {

	private String dateTime;
	private String value;
	
	public CommonDataSample() {
		super();
	}
	
	public CommonDataSample(String dateTime, String value) {
		super();
		this.dateTime = dateTime;
		this.value = value;
	}

	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DataSample [dateTime=" + dateTime + ", value=" + value + "]";
	}
	
}
