package com.grad.controller;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;

import com.grad.services.builders.ButtonsBuilderService;
import com.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
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

		@Autowired
		private FieldsBuilderService fieldsService;

		@Autowired
		private ButtonsBuilderService buttonsService;

		@Override
		public void init(VaadinRequest request) {
			VerticalLayout content = new VerticalLayout();
			setContent(content);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			ProgressBar bar = new ProgressBar(0.0f);
			bar.setWidth("800");
			bar.setDescription("Operations progress");

			DateField startDate = new DateField();
			fieldsService.dateBuilder(startDate);

			DateField endDate = new DateField();
			fieldsService.dateBuilder(endDate);

			TextField heartRate = new TextField();
			fieldsService.heartRateBuilder(heartRate);

			TextField mail = new TextField();
			fieldsService.mailBuilder(mail);

			TextField clientId = new TextField();
			fieldsService.clientIdBuilder(clientId);

			TextField clientSecret = new TextField();
			fieldsService.clientSecretBuilder(clientSecret);

			Button collections = new Button();
			buttonsService.collectionsBuilder(collections, bar);

			Button authorizationCode = new Button();
			buttonsService.authorizationBuilder(authorizationCode, bar, clientId, clientSecret);

			Button submitDates = new Button();
			buttonsService.submitDates(submitDates, bar, startDate, endDate);

			Button heart = new Button();
			buttonsService.heartBuilder(heart, bar, startDate, endDate);

			Button sleep = new Button();
			buttonsService.sleepBuilder(sleep, bar, startDate, endDate);

			Button activities = new Button();
			buttonsService.activitiesBuilder(activities, bar, startDate, endDate);

			Button other = new Button();
			buttonsService.otherBuilder(other, bar);

			Button profile = new Button();
			buttonsService.profileBuilder(profile, bar);

			Button heartRateMail = new Button();
			buttonsService.heartRateMailBuilder(heartRateMail, bar, mail, heartRate);

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
			content.addComponent(new Label("Push to start receiving and saving user data associated with heart rate"));
			content.addComponent(heart);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Push to start receiving and saving user data associated with his profile"));
			content.addComponent(profile);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label(
					"Push to start receiving and saving user data associated with sleep (minutes asleep, time in bed, minutes awake, sleep efficiency, minutes to fall asleep)"));
			content.addComponent(sleep);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label(
					"Push to start receiving and saving user data associated with activities (steps, floors, calories, distance)"));
			content.addComponent(activities);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label(
					"Push to start receiving and saving user data associated with lifetime activities and frequence activities"));
			content.addComponent(other);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label("Complete the next 2 fields to continue"));
			content.addComponent(mail);
			content.addComponent(heartRate);
			content.addComponent(heartRateMail);
			content.addComponent(new Label("\n"));
			content.addComponent(bar);
		}
	}
}
