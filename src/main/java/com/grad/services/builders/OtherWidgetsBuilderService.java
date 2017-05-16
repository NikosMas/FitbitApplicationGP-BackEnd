package com.grad.services.builders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.grad.domain.HeartRateCategory;
import com.vaadin.server.FileResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OtherWidgetsBuilderService {

	/**
	 * @param image
	 * @param bar
	 * @param select
	 * @param multiCheckBox
	 */
	public void other(Image image, ProgressBar bar, ComboBox<HeartRateCategory> select,
			CheckBoxGroup<String> multiCheckBox) {

		image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));
		bar.setWidth("800");
		bar.setDescription("Operations progress");

		List<HeartRateCategory> planets = new ArrayList<>();
		planets.add(HeartRateCategory.OUT_OF_RANGE);
		planets.add(HeartRateCategory.FAT_BURN);
		planets.add(HeartRateCategory.CARDIO);
		planets.add(HeartRateCategory.PEAK);

		select.setItems(planets);
		select.setItemCaptionGenerator(HeartRateCategory::description);
		select.setPlaceholder("heart-rate category");
		select.setWidth("250");
		select.setEmptySelectionAllowed(false);

		multiCheckBox.setItems("Sleep data", "Profile data", "Activities data", "Lifetime activities data",
				"Frequent activities data", "HeartRate data");
		multiCheckBox.setEnabled(false);
	}

	/**
	 * @param content
	 * @param image
	 * @param bar
	 * @param select
	 * @param multiCheckBox
	 * @param startDate
	 * @param endDate
	 * @param heartRate
	 * @param mail
	 * @param clientId
	 * @param clientSecret
	 * @param collections
	 * @param authorizationCode
	 * @param submitDates
	 * @param heartRateMail
	 * @param submitCheckBoxButton
	 * @param exit
	 * @param complete
	 */
	public void contentSetters(VerticalLayout content, Image image, ProgressBar bar, ComboBox<HeartRateCategory> select,
			CheckBoxGroup<String> multiCheckBox, DateField startDate, DateField endDate, TextField heartRate,
			TextField mail, TextField clientId, TextField clientSecret, Button collections, Button authorizationCode,
			Button submitDates, Button heartRateMail, Button submitCheckBoxButton, Button exit, Button complete) {

		content.addComponent(image);
		content.addComponent(new Label("Push to start creating the collections into Mongo database"));
		content.addComponent(collections);
		content.addComponent(new Label("\n"));
		content.addComponent(clientId);
		content.addComponent(clientSecret);
		content.addComponent(new Label(
				"Push to start connecting with Fitbit API for recieving the authorization code required to next calls to the API"));
		content.addComponent(authorizationCode);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Pick the dates in which range the application will use for the data calls"));
		content.addComponent(startDate);
		content.addComponent(endDate);
		content.addComponent(submitDates);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Pick the categories you want to retrieve data from fitbit API"));
		content.addComponent(multiCheckBox);
		content.addComponent(submitCheckBoxButton);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Complete the next 3 fields to continue with e-mail process"));
		content.addComponent(mail);
		content.addComponent(select);
		content.addComponent(heartRate);
		content.addComponent(heartRateMail);
		content.addComponent(new Label("\n"));
		content.addComponent(bar);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push if you want to repeat the process"));
		content.addComponent(complete);
		content.addComponent(new Label("\n"));
		content.addComponent(new Label("Push to exit and stop all processes"));
		content.addComponent(exit);

	}

}
