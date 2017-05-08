package com.grad.services.builders;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.stereotype.Service;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

@Service
public class FieldsBuilderService {

	public DateField dateBuilder(DateField date) {
		date.setDateFormat("yyyy-MM-dd");
		date.setPlaceholder("yyyy-mm-dd");
		date.setCaption("Pick the start date of downloading data");
		date.setRangeStart(LocalDate.of(2014, Month.DECEMBER, 31));
		date.setRangeEnd(LocalDate.now());
		date.setShowISOWeekNumbers(true);
		date.setDateOutOfRangeMessage("The date you picked is out of range with available dates");
		return date;
	}

	public TextField mailBuilder(TextField mail) {
		mail.setCaption(
				"Put the mail address that you want the application send information about high heart rate values");
		mail.setWidth("250");
		mail.setPlaceholder("e-mail");
		mail.setIcon(VaadinIcons.ENVELOPE_O);
		return mail;
	}

	public TextField heartRateBuilder(TextField heartRate) {
		heartRate.setCaption("Put the minimum number of minutes that user's heart rate was at its peak");
		heartRate.setWidth("250");
		heartRate.setPlaceholder("minutes");
		heartRate.setIcon(VaadinIcons.CLOCK);
		return heartRate;
	}

	public TextField clientIdBuilder(TextField clientId) {
		clientId.setCaption("Put the clientId of your application to Fitbit server");
		clientId.setWidth("250");
		clientId.setPlaceholder("client id");
		clientId.setIcon(VaadinIcons.USER);
		return clientId;
	}

	public TextField clientSecretBuilder(TextField clientSecret) {
		clientSecret.setCaption("Put the clientSecret of your application to Fitbit server");
		clientSecret.setWidth("250");
		clientSecret.setPlaceholder("client secret");
		clientSecret.setIcon(VaadinIcons.PASSWORD);
		return clientSecret;
	}

}
