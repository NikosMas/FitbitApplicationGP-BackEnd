package com.grad.services.builders;

import org.springframework.stereotype.Service;

import com.grad.domain.HeartRateCategory;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Service about Vaadin content building 
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class ContentBuilderService {

	/**
	 * @param continueProcess
	 * @param exit
	 * @param authorizationCode
	 * @param collections
	 * @param clientSecret
	 * @param clientId
	 * @param clientSecretImage
	 * @param clientIdImage
	 * @param image
	 * @param content
	 * @param image
	 * @param restart 
	 * @param file
	 */
	public void dashboardContentBuilder(VerticalLayout content, Image image, Image clientIdImage,
			Image clientSecretImage, TextField clientId, TextField clientSecret, Button collections,
			Button authorizationCode, Button exit, Button continueProcess, Button restart) {
		content.addComponent(image);
		content.addComponent(new Label("Push to start creating the collections into Mongo database"));
		content.addComponent(collections);
		content.addComponent(new Label("\n"));
		content.addComponent(clientIdImage);
		content.addComponent(clientId);
		content.addComponent(new Label("\n"));
		content.addComponent(clientSecretImage);
		content.addComponent(clientSecret);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label(
				"Push to start connecting with Fitbit API for recieving the authorization code required to next calls to the API"));
		content.addComponent(authorizationCode);
		content.addComponent(new Label("\n"));
		content.addComponent(continueProcess);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to exit and stop all processes"));
		content.addComponent(exit);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to refresh"));
		content.addComponent(restart);
	}

	/**
	 * @param content
	 * @param image
	 * @param multiCheckBox
	 * @param startDate
	 * @param endDate
	 * @param heartRate
	 * @param submitDates
	 * @param submitCheckBoxButton
	 * @param exit
	 * @param continueProcess
	 * @param restart 
	 */
	public void userDataContentBuilder(VerticalLayout content, Image image, CheckBoxGroup<String> multiCheckBox,
			DateField startDate, DateField endDate, TextField heartRate, Button submitDates,
			Button submitCheckBoxButton, Button exit, Button continueProcess, Button restart) {

		content.addComponent(image);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Pick the date range that the application will use for the data calls"));
		content.addComponent(startDate);
		content.addComponent(endDate);
		content.addComponent(new Label("\n"));
		content.addComponent(submitDates);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Pick the categories you want to retrieve data from fitbit API"));
		content.addComponent(multiCheckBox);
		content.addComponent(new Label("\n"));
		content.addComponent(submitCheckBoxButton);
		content.addComponent(new Label("\n"));
		content.addComponent(continueProcess);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to exit and stop all processes"));
		content.addComponent(exit);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to restart the process"));
		content.addComponent(restart);
	}

	/**
	 * @param content
	 * @param image
	 * @param select
	 * @param heartRate
	 * @param mail
	 * @param heartRateMail
	 * @param exit
	 * @param restart 
	 */
	public void heartRateFilterContentBuilder(VerticalLayout content, Image image, ComboBox<HeartRateCategory> select,
			TextField heartRate, TextField mail, Button heartRateMail, Button exit, Button restart) {

		content.addComponent(image);
		content.addComponent(new Label("Complete the next 3 fields to continue with e-mail process"));
		content.addComponent(mail);
		content.addComponent(new Label("\n"));
		content.addComponent(select);
		content.addComponent(new Label("\n"));
		content.addComponent(heartRate);
		content.addComponent(new Label("\n"));
		content.addComponent(heartRateMail);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to exit and stop all processes"));
		content.addComponent(exit);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to restart the process"));
		content.addComponent(restart);
	}

	public void finalizeContentBuilder(VerticalLayout content, Image image, Button restart) {
		content.addComponent(image);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to restart the process"));
		content.addComponent(restart);
		content.addComponent(new Label("\n"));
	}

}
