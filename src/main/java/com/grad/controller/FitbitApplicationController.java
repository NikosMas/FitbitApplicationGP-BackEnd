package com.grad.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.CheckBoxBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
public class FitbitApplicationController {

	@Title("FitbitApplication")
	@SpringUI(path = "home")
	public static class VaadinUI extends UI {

		private static final long serialVersionUID = 1L;
		private List<DateField> dateFields = new ArrayList<DateField>();
		private List<TextField> textFields = new ArrayList<TextField>();
		private List<Button> buttons = new ArrayList<Button>();

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Autowired
		private CheckBoxBuilderService checkBoxService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);
			setResponsive(true);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			ProgressBar bar = new ProgressBar(0.0f);
			bar.setWidth("800");
			bar.setDescription("Operations progress");

			DateField startDate = new DateField();
			fieldsService.dateBuilder(startDate);
			dateFields.add(startDate);

			DateField endDate = new DateField();
			fieldsService.dateBuilder(endDate);
			dateFields.add(endDate);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);
			textFields.add(heartRate);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);
			textFields.add(mail);

			TextField clientId = new TextField();
			fieldsService.clientIdBuilder(clientId);
			textFields.add(clientId);

			TextField clientSecret = new TextField();
			fieldsService.clientSecretBuilder(clientSecret);
			textFields.add(clientSecret);

			Button collections = new Button();
			buttonsService.collectionsBuilder(collections, bar);
			buttons.add(collections);

			Button authorizationCode = new Button();
			buttonsService.authorizationBuilder(authorizationCode, bar, clientId, clientSecret);
			buttons.add(authorizationCode);

			CheckBoxGroup<String> multiCheckBox = new CheckBoxGroup<>("User data categories");
			multiCheckBox.setItems("Sleep data", "Profile data", "Activities data", "Lifetime activities data",
					"Frequent activities data", "HeartRate data");
			multiCheckBox.setEnabled(false);

			Button submitDates = new Button();
			checkBoxService.submitDates(submitDates, bar, startDate, endDate, multiCheckBox);
			buttons.add(submitDates);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, bar, mail, heartRate, multiCheckBox);
			buttons.add(heartRateMail);

			Button submitCheckBoxButton = new Button();
			buttons.add(submitCheckBoxButton);

			checkBoxService.checkBoxButton(multiCheckBox, submitCheckBoxButton, bar, startDate, endDate, heartRateMail,
					content);

			Button exit = new Button();
			buttonsService.exitBuilder(exit, content);
			buttons.add(exit);

			Button complete = new Button();
			buttons.add(complete);
			buttonsService.completeBuilder(complete, bar, dateFields, textFields, buttons, multiCheckBox);

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
			content.addComponent(
					new Label("Pick the dates in which range the application will use for the data calls"));
			content.addComponent(startDate);
			content.addComponent(endDate);
			content.addComponent(submitDates);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Pick the categories you want to retrieve data from fitbit API"));
			content.addComponent(multiCheckBox);
			content.addComponent(submitCheckBoxButton);
			content.addComponent(new Label("Complete the next 2 fields to continue"));
			content.addComponent(mail);
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
}
