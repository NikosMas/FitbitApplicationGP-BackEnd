package com.grad.services.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.grad.services.auth.AuthCodeRequestService;
import com.grad.services.calendar.CalendarService;
import com.grad.services.collections.CreateCollectionsService;
import com.grad.services.data.ActivitiesDataService;
import com.grad.services.data.HeartDataService;
import com.grad.services.data.OtherDataService;
import com.grad.services.data.SleepDataService;
import com.grad.services.mail.FitbitHeartCheckPeakService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ButtonsBuilderService {

	private FitbitHeartCheckPeakService heartPeakService;
	private CreateCollectionsService collectionsService;
	private AuthCodeRequestService codeService;
	private ActivitiesDataService activitiesService;
	private HeartDataService heartService;
	private OtherDataService otherService;
	private SleepDataService sleepService;
	private CalendarService calendarService;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	List<Map<String, String>> dates = new ArrayList<>();

	@Autowired
	public ButtonsBuilderService(FitbitHeartCheckPeakService heartPeakService,
			CreateCollectionsService collectionsService, AuthCodeRequestService codeService,
			ActivitiesDataService activitiesService, HeartDataService heartService, OtherDataService otherService,
			SleepDataService sleepService, CalendarService calendarService) {
		this.codeService = codeService;
		this.collectionsService = collectionsService;
		this.heartPeakService = heartPeakService;
		this.activitiesService = activitiesService;
		this.heartService = heartService;
		this.otherService = otherService;
		this.sleepService = sleepService;
		this.calendarService = calendarService;
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * @param collections
	 * @param bar
	 * @return
	 */
	public Button collectionsBuilder(Button collections, ProgressBar bar) {
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

		return collections;
	}

	/**
	 * @param authorizationCode
	 * @param bar
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	public Button authorizationBuilder(Button authorizationCode, ProgressBar bar, TextField clientId,
			TextField clientSecret) {
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

		return authorizationCode;
	}

	/**
	 * @param submitDates
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button submitDates(Button submitDates, ProgressBar bar, DateField startDate, DateField endDate) {
		submitDates.setIcon(VaadinIcons.CHECK_CIRCLE);
		submitDates.setCaption("Submit");
		submitDates.setWidth("150");
		submitDates.addClickListener(click -> {
			float current = bar.getValue();
			if (!startDate.isEmpty() && !endDate.isEmpty() && startDate.getValue().isBefore(endDate.getValue())
					&& current > 0.2f && current < 1.0f) {
				submitDates.setVisible(false);
				dates = calendarService.getDates(startDate.getValue(), endDate.getValue());
			} else {
				Notification.show("You missed some steps before or dates given are invalid", Type.ERROR_MESSAGE);
			}
		});

		return submitDates;
	}

	/**
	 * @param heart
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button heartBuilder(Button heart, ProgressBar bar, DateField startDate, DateField endDate) {
		heart.setIcon(VaadinIcons.PLAY);
		heart.setCaption("Start");
		heart.setWidth("150");
		heart.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.2f && current < 1.0f && startDate != null && endDate != null) {
				heart.setVisible(false);
				if (heartService.filterHeartRateValues(dates)) {
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

		return heart;
	}

	/**
	 * @param sleep
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button sleepBuilder(Button sleep, ProgressBar bar, DateField startDate, DateField endDate) {
		sleep.setIcon(VaadinIcons.PLAY);
		sleep.setCaption("Start");
		sleep.setWidth("150");
		sleep.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.2f && current < 1.0f && startDate != null && endDate != null) {
				sleep.setVisible(false);
				if (sleepService.sleep(dates)) {
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

		return sleep;
	}

	/**
	 * @param activities
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button activitiesBuilder(Button activities, ProgressBar bar, DateField startDate, DateField endDate) {
		activities.setIcon(VaadinIcons.PLAY);
		activities.setCaption("Start");
		activities.setWidth("150");
		activities.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.2f && current < 1.0f && startDate != null && endDate != null) {
				activities.setVisible(false);
				if (activitiesService.activities(dates)) {
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

		return activities;
	}

	/**
	 * @param other
	 * @param bar
	 * @return
	 */
	public Button otherBuilder(Button other, ProgressBar bar) {
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

		return other;
	}

	/**
	 * @param profile
	 * @param bar
	 * @return
	 */
	public Button profileBuilder(Button profile, ProgressBar bar) {
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

		return profile;
	}

	/**
	 * @param heartRateMail
	 * @param bar
	 * @param mail
	 * @param heartRate
	 * @return
	 */
	public Button heartRateMailBuilder(Button heartRateMail, ProgressBar bar, TextField mail, TextField heartRate) {
		heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
		heartRateMail.setCaption("Submit");
		heartRateMail.setWidth("150");
		heartRateMail.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.8f && current < 1.0f) {
				if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty() && mail.getValue().contains("@")) {
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

		return heartRateMail;
	}
}
