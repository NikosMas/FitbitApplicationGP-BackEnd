package com.grad.controller;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

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
		private FitbitHeartCheckPeakService heartPeakService;
		private CreateCollectionsService collectionsService;
		private AuthCodeRequestService codeService;
		private ActivitiesDataService activitiesService;
		private HeartDataService heartService;
		private OtherDataService otherService;
		private SleepDataService sleepService;
		private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

		@Autowired
		public VaadinUI(FitbitHeartCheckPeakService heartPeakService, CreateCollectionsService collectionsService,
				AuthCodeRequestService codeService, ActivitiesDataService activitiesService,
				HeartDataService heartService, OtherDataService otherService, SleepDataService sleepService) {

			this.codeService = codeService;
			this.collectionsService = collectionsService;
			this.heartPeakService = heartPeakService;
			this.activitiesService = activitiesService;
			this.heartService = heartService;
			this.otherService = otherService;
			this.sleepService = sleepService;
		}

		@Autowired
		private RedisTemplate<String, String> redisTemplate;

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

			TextField clientId = new TextField();
			clientId.setCaption("Put the clientId of your application to Fitbit server");
			clientId.setWidth("250");
			clientId.setPlaceholder("client id");
			clientId.setIcon(VaadinIcons.USER);

			TextField clientSecret = new TextField();
			clientSecret.setCaption("Put the clientSecret of your application to Fitbit server");
			clientSecret.setWidth("250");
			clientSecret.setPlaceholder("client secret");
			clientSecret.setIcon(VaadinIcons.PASSWORD);

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
				if (collectionsService.collectionsCreate()) {
					float current = bar.getValue();
					if (current < 1.0f)
						bar.setValue(current + 0.125f);
					LOG.info("Collections created successfully into Mongo database");
					Notification.show("Collections created successfully!");
				} else {
					Notification.show("Something went wrong! Check if \"fitbit\" database exists", Type.ERROR_MESSAGE);
				}
			});

			Button authorizationCode = new Button();
			authorizationCode.setIcon(VaadinIcons.CHECK_CIRCLE);
			authorizationCode.setCaption("Submit");
			authorizationCode.setWidth("150");
			authorizationCode.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.1 && current < 1.0f) {
					if (!(clientId.isEmpty() || clientSecret.isEmpty())) {
						redisTemplate.opsForValue().set("Client-id", clientId.getValue());
						redisTemplate.opsForValue().set("Client-secret", clientSecret.getValue());
						authorizationCode.setVisible(false);
						codeService.codeRequest();
						bar.setValue(current + 0.125f);
						LOG.info("Authorization code saved into Redis database and it's ready for use");
						Notification.show("Authorization code saved into Redis database and it's ready for use!");
					} else {
						Notification.show(
								"Complete with valid client id and client secret given from to your account at Fitbit",
								Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button heart = new Button();
			heart.setIcon(VaadinIcons.PLAY);
			heart.setCaption("Start");
			heart.setWidth("150");
			heart.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.2f && current < 1.0f) {
					heart.setVisible(false);
					if (heartService.filterHeartRateValues()) {
						bar.setValue(current + 0.125f);
						LOG.info("Heart rate data recieved and stored to database");
						Notification.show("User data stored successfully!");
					} else {
						Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button sleep = new Button();
			sleep.setIcon(VaadinIcons.PLAY);
			sleep.setCaption("Start");
			sleep.setWidth("150");
			sleep.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.2f && current < 1.0f) {
					sleep.setVisible(false);
					if (sleepService.sleep()) {
						bar.setValue(current + 0.125f);
						LOG.info("Sleep data recieved and stored to database");
						Notification.show("User data stored successfully!");
					} else {
						Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button activities = new Button();
			activities.setIcon(VaadinIcons.PLAY);
			activities.setCaption("Start");
			activities.setWidth("150");
			activities.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.2f && current < 1.0f) {
					activities.setVisible(false);
					if (activitiesService.activities()) {
						bar.setValue(current + 0.125f);
						LOG.info("Activities data recieved and stored to database");
						Notification.show("User data stored successfully!");
					} else {
						Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button other = new Button();
			other.setIcon(VaadinIcons.PLAY);
			other.setCaption("Start");
			other.setWidth("150");
			other.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.2f && current < 1.0f) {
					other.setVisible(false);
					if (otherService.lifetime() && otherService.frequence()) {
						bar.setValue(current + 0.125f);
						LOG.info("Lifetime and frequence data recieved and stored to database");
						Notification.show("User data stored successfully!");
					} else {
						Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button profile = new Button();
			profile.setIcon(VaadinIcons.PLAY);
			profile.setCaption("Start");
			profile.setWidth("150");
			profile.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.2f && current < 1.0f) {
					profile.setVisible(false);
					if (otherService.profile()) {
						bar.setValue(current + 0.125f);
						LOG.info("Profile data recieved and stored to database");
						Notification.show("User data stored successfully!");
					} else {
						Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before do this", Type.ERROR_MESSAGE);
				}
			});

			Button heartRateMail = new Button();
			heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
			heartRateMail.setCaption("Submit");
			heartRateMail.setWidth("150");
			heartRateMail.addClickListener(click -> {
				float current = bar.getValue();
				if (current > 0.8f && current < 1.0f) {
					if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty()
							&& mail.getValue().contains("@")) {
						heartRateMail.setVisible(false);
						heartPeakService.heartRateSelect(mail.getValue(), heartRate.getValue());
						bar.setValue(current + 0.125f);
						LOG.info("Mail successfully sent to user with heart rate information");
						Notification.show("Mail successfully sent to user with heart rate information!");
					} else {
						Notification.show("Complete the required fields with a valid e-mail & number of minutes",
								Type.ERROR_MESSAGE);
					}
				} else {
					Notification.show("Complete the required steps before send the e-mail", Type.ERROR_MESSAGE);
				}
			});

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
