package com.fitbit.grad.models;

/**
 * model class required at saving service
 *
 * @author nikos_mas, alex_kak
 */

public class CommonDataSample {

    private String dateTime;
    private String value;

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
