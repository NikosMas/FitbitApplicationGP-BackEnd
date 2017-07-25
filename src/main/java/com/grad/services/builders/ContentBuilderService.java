package com.grad.services.builders;

import org.springframework.stereotype.Service;

import com.grad.domain.HeartRateCategory;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
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
	 * @param restart 
	 * @param stepForward 
	 * @param exit 
	 * @param authorizationCode 
	 * @param clientSecret 
	 * @param clientId 
	 * @param image 
	 * @param content 
	 * @param continueProcess
	 * @param exit
	 * @param authorizationCode
	 * @param collections
	 * @param clientSecret
	 * @param clientId
	 * @param image
	 * @param content
	 * @param image
	 * @param file
	 */
	public void dashboardContentBuilder(VerticalLayout content, Image image, TextField clientId, TextField clientSecret, Button authorizationCode, Button exit, Button stepForward, Button restart) {
		content.addComponent(image);
		content.addComponent(new Label("\n"));
		content.addComponent(clientId);
		content.addComponent(new Label("\n"));
		content.addComponent(clientSecret);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label(
				"Click to complete the authorization."));
		content.addComponent(authorizationCode);
		content.addComponent(new Label("\n"));
		content.addComponent(stepForward);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Click to refresh"));
		content.addComponent(restart);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Click to exit the application"));
		content.addComponent(exit);
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
		content.addComponent(new Label("Choose the information you want to get."));
		content.addComponent(multiCheckBox);
		content.addComponent(new Label("\n"));
		content.addComponent(submitCheckBoxButton);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Go to heart rate notification tab if you checked heart rate data or exit if you didn't"));
		content.addComponent(continueProcess);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Click to go back to authorization tab"));
		content.addComponent(restart);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Click to exit the application"));
		content.addComponent(exit);
	}

	/**
	 * @param content
	 * @param image
	 * @param select
	 * @param heartRate
	 * @param mail
	 * @param heartRateMail
	 * @param exit
	 */
	public void heartRateFilterContentBuilder(VerticalLayout content, Image image, ComboBox<HeartRateCategory> select,
			TextField heartRate, TextField mail, Button heartRateMail, Button exit) {

		content.addComponent(image);
		content.addComponent(new Label("Complete the next 3 fields to continue with e-mail notification."));
		content.addComponent(mail);
		content.addComponent(new Label("\n"));
		content.addComponent(select);
		content.addComponent(new Label("\n"));
		content.addComponent(heartRate);
		content.addComponent(new Label("\n"));
		content.addComponent(heartRateMail);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Click to exit the application"));
		content.addComponent(exit);
		content.addComponent(new Label("\n"));
	}

	public void finalizeContentBuilder(VerticalLayout content, Image image, RadioButtonGroup<String> group, Button restart) {
		content.addComponent(image);
		content.addComponent(new Label("\n"));
		content.addComponent(group);
		content.addComponent(new Label("Click to continue"));
		content.addComponent(restart);
		content.addComponent(new Label("\n"));
	}

}
