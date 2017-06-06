package com.grad.services.builders;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.stereotype.Service;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

/**
 * Service about Vaadin text & date fields building 
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class FieldsBuilderService {

	/**
	 * @param date
	 */
	public void dateBuilder(DateField date) {
		date.setDateFormat("yyyy-MM-dd");
		date.setPlaceholder("yyyy-mm-dd");
		date.setCaption("Pick a valid date");
		date.setRangeStart(LocalDate.of(2014, Month.DECEMBER, 31));
		date.setRangeEnd(LocalDate.now());
		date.setShowISOWeekNumbers(true);
		date.setDateOutOfRangeMessage("The date you picked is out of range with available dates");
	}

	/**
	 * @param mail
	 */
	public void mailBuilder(TextField mail) {
		mail.setCaption(
				"Put the mail address that you want the application send information about high heart rate values");
		mail.setWidth("250");
		mail.setPlaceholder("e-mail");
		mail.setIcon(VaadinIcons.ENVELOPE_O);
	}

	/**
	 * @param heartRate
	 */
	public void heartRateBuilder(TextField heartRate) {
		heartRate.setCaption(
				"Write the minimum number of minutes that you want to filter out by the category you chose");
		heartRate.setWidth("250");
		heartRate.setPlaceholder("minutes");
		heartRate.setIcon(VaadinIcons.CLOCK);
	}

	/**
	 * @param clientId
	 */
	public void clientIdBuilder(TextField clientId) {
		clientId.setCaption("Put the clientId of your application at -> dev.fitbit.com");
		clientId.setWidth("250");
		clientId.setPlaceholder("client id");
		clientId.setIcon(VaadinIcons.USER);
	}

	/**
	 * @param clientSecret
	 */
	public void clientSecretBuilder(TextField clientSecret) {
		clientSecret.setCaption("Put the clientSecret of your application at -> dev.fitbit.com");
		clientSecret.setWidth("250");
		clientSecret.setPlaceholder("client secret");
		clientSecret.setIcon(VaadinIcons.PASSWORD);
	}

}
