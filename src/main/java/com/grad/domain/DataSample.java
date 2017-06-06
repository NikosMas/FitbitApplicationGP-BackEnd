package com.grad.domain;

/**
 * model class required at saving service  
 * 
 * @author nikos_mas, alex_kak
 */

public class DataSample {

	private String dateTime;
	private String value;
	
	public DataSample() {
		super();
	}
	
	public DataSample(String dateTime, String value) {
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
	
}
