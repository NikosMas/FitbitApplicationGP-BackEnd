package com.grad.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.grad.auth.services.AuthCodeRequestService;
import com.grad.collections.CreateCollectionsService;
import com.grad.data.services.ActivitiesDataService;
import com.grad.data.services.HeartDataService;
import com.grad.data.services.OtherDataService;
import com.grad.data.services.SleepDataService;
import com.grad.heart.services.FitbitHeartCheckPeakService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
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
		private FitbitHeartCheckPeakService heartService;
		private CreateCollectionsService collectionsService;
		private AuthCodeRequestService codeService;
		private ActivitiesDataService activitiesDataStore;
		private HeartDataService heartDataStore;
		private OtherDataService otherDataStore;
		private SleepDataService sleepDataStore;
		private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

		@Autowired
		public VaadinUI(FitbitHeartCheckPeakService heartService, CreateCollectionsService collectionsService,
				AuthCodeRequestService codeService, ActivitiesDataService activitiesDataStore,
				HeartDataService heartDataStore, OtherDataService otherDataStore, SleepDataService sleepDataStore) {

			this.codeService = codeService;
			this.collectionsService = collectionsService;
			this.heartService = heartService;
			this.activitiesDataStore = activitiesDataStore;
			this.heartDataStore = heartDataStore;
			this.otherDataStore = otherDataStore;
			this.sleepDataStore = sleepDataStore;
		}

		@Override
		public void init(VaadinRequest request) {

			VerticalLayout content = new VerticalLayout();
			setContent(content);

			TextField heartRate = new TextField();
			heartRate.setCaption("Put the minimum number of minutes that user's heart rate was at its peak");
			heartRate.setWidth("250");
			heartRate.setPlaceholder("minutes");
			heartRate.setIcon(VaadinIcons.CLOCK);

			TextField mail = new TextField();
			mail.setCaption(
					"Put the mail address that you want the application send information about high heart rate values");
			mail.setWidth("250");
			mail.setPlaceholder("e-mail");
			mail.setIcon(VaadinIcons.ENVELOPE_O);

			Image image = new Image();
			image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

			ProgressBar bar = new ProgressBar(0.0f);
			bar.setWidth("800");
			bar.setDescription("Operations progress");

			Button collections = new Button();
			collections.setIcon(VaadinIcons.PLAY);
			collections.setCaption("Start");
			collections.setWidth("150");
			collections.addClickListener(click -> {
				collections.setVisible(false);
				collectionsService.collectionsCreate();
				Notification.show("Collections created successfully!");
				float current = bar.getValue();
				if (current < 1.0f)
					bar.setValue(current + 0.125f);
			});

			Button authorizationCode = new Button();
			authorizationCode.setIcon(VaadinIcons.PLAY);
			authorizationCode.setCaption("Start");
			authorizationCode.setWidth("150");
			authorizationCode.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.1 && current < 1.0f) {
						authorizationCode.setVisible(false);
						codeService.codeRequest();
						bar.setValue(current + 0.125f);
						Notification.show("Authorization code saved into Redis database and it's ready for use!");
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException | InterruptedException | URISyntaxException e) {
					LOG.error(e.toString());
				}
			});

			Button heart = new Button();
			heart.setIcon(VaadinIcons.PLAY);
			heart.setCaption("Start");
			heart.setWidth("150");
			heart.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.2f && current < 1.0f) {
						heart.setVisible(false);
						heartDataStore.heart();
						LOG.info("Heart rate data recieved and stored to database");
						Notification.show("User data stored successfully!");
						bar.setValue(current + 0.125f);
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					LOG.error(e.toString());
				}
			});

			Button sleep = new Button();
			sleep.setIcon(VaadinIcons.PLAY);
			sleep.setCaption("Start");
			sleep.setWidth("150");
			sleep.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.2f && current < 1.0f) {
						sleep.setVisible(false);
						sleepDataStore.sleep();
						LOG.info("Sleep data recieved and stored to database");
						Notification.show("User data stored successfully!");
						bar.setValue(current + 0.125f);
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					LOG.error(e.toString());
				}
			});

			Button activities = new Button();
			activities.setIcon(VaadinIcons.PLAY);
			activities.setCaption("Start");
			activities.setWidth("150");
			activities.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.2f && current < 1.0f) {
						activities.setVisible(false);
						activitiesDataStore.activities();
						LOG.info("Activities data recieved and stored to database");
						Notification.show("User data stored successfully!");
						bar.setValue(current + 0.125f);
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					LOG.error(e.toString());
				}
			});

			Button other = new Button();
			other.setIcon(VaadinIcons.PLAY);
			other.setCaption("Start");
			other.setWidth("150");
			other.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.2f && current < 1.0f) {
						other.setVisible(false);
						otherDataStore.lifetime();
						otherDataStore.frequence();
						LOG.info("Lifetime and frequence data recieved and stored to database");
						Notification.show("User data stored successfully!");
						bar.setValue(current + 0.125f);
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					LOG.error(e.toString());
				}
			});

			Button profile = new Button();
			profile.setIcon(VaadinIcons.PLAY);
			profile.setCaption("Start");
			profile.setWidth("150");
			profile.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.2f && current < 1.0f) {
						profile.setVisible(false);
						otherDataStore.profile();
						LOG.info("Profile data recieved and stored to database");
						Notification.show("User data stored successfully!");
						bar.setValue(current + 0.125f);
					} else {
						Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
					}
				} catch (IOException e) {
					LOG.error(e.toString());
				}
			});

			Button heartRateMail = new Button();
			heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
			heartRateMail.setCaption("Submit");
			heartRateMail.setWidth("150");
			heartRateMail.addClickListener(click -> {
				try {
					float current = bar.getValue();
					if (current > 0.8f && current < 1.0f) {
						if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty()
								&& mail.getValue().contains("@")) {
							heartRateMail.setVisible(false);
							heartService.heartRateSelect(mail.getValue(), heartRate.getValue());
							Notification.show("Mail successfully sent to user with heart rate information");
							bar.setValue(current + 0.125f);
						} else {
							Notification.show("Complete the required fields with a valid e-mail & number of minutes",
									Type.ERROR_MESSAGE);
						}
					} else {
						Notification.show("Complete the required steps before send the e-mail", Type.ERROR_MESSAGE);
					}
				} catch (IOException | MessagingException e) {
					LOG.error(e.toString());
				}
			});

			content.addComponent(image);
			content.addComponent(new Label("Push to start creating the collections into Mongo database"));
			content.addComponent(collections);
			content.addComponent(new Label("\n"));
			content.addComponent(new Label(
					"Push to start connecting with Fitbit API for recieving the authorization code required to next calls to the API"));
			content.addComponent(authorizationCode);
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
