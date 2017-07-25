package com.grad.services.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.grad.domain.HeartRateCategoryEnum;
import com.grad.services.auth.AuthCodeRequestService;
import com.grad.services.collections.CollectionService;
import com.grad.services.data.DailyDataService;
import com.grad.services.mail.HeartRateFilterService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * Service about Vaadin buttons building
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class ButtonsBuilderService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	List<Map<String, String>> dates = new ArrayList<>();

	@Autowired
	private HeartRateFilterService heartPeakService;

	@Autowired
	private CollectionService collectionsService;

	@Autowired
	private AuthCodeRequestService codeService;

	@Autowired
	private ClearAllBuilderService clearFieldsService;

	@Autowired
	DailyDataService dailyDataService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * @param collections
	 * @return
	 */
	public void collectionsBuilder(Button collections) {
		collections.setIcon(VaadinIcons.PLAY);
		collections.setCaption("Start");
		collections.setWidth("150");
		collections.addClickListener(click -> {
			collections.setEnabled(false);
			collectionsService.collectionsCreate();
			LOG.info("Collections created successfully into Mongo database");
			Notification.show("Collections created successfully!");
		});
	}

	/**
	 * @param authorizationCode
	 * @param clientId
	 * @param clientSecret
	 * @param exit 
	 * @return
	 */
	public void authorizationBuilder(Button authorizationCode, TextField clientId, TextField clientSecret, Button exit) {
		authorizationCode.setIcon(VaadinIcons.CHECK_CIRCLE);
		authorizationCode.setCaption("Submit");
		authorizationCode.setWidth("150");
		authorizationCode.addClickListener(click -> {
			if (!(clientId.isEmpty() || clientSecret.isEmpty())) {
				redisTemplate.opsForValue().set("Client-id", clientId.getValue());
				redisTemplate.opsForValue().set("Client-secret", clientSecret.getValue());
				authorizationCode.setEnabled(false);
				clientId.setEnabled(false);
				clientSecret.setEnabled(false);
				codeService.codeRequest();
				collectionsService.collectionsCreate();
				exit.setEnabled(true);
				Notification.show("Authorization code saved into Redis database and it's ready for use!");
			} else {
				Notification.show(
						"Complete with valid client id and client secret given from to your account at Fitbit",
						Type.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * @param heartRateMail
	 * @param mail
	 * @param heartRate
	 * @param select
	 * @param content
	 */
	public void heartRateMailBuilder(Button heartRateMail, TextField mail, TextField heartRate,
			ComboBox<HeartRateCategoryEnum> select, VerticalLayout content) {
		heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
		heartRateMail.setCaption("Submit");
		heartRateMail.setWidth("150");
		heartRateMail.addClickListener(click -> {
			if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty() && mail.getValue().contains("@")
					&& !select.isEmpty()) {
				try {
					heartPeakService.heartRateSelect(mail.getValue(), Long.valueOf(heartRate.getValue()),
							select.getValue(), content);
					heartRateMail.setEnabled(false);
					select.setEnabled(false);
					mail.setEnabled(false);
					heartRate.setEnabled(false);
					LOG.info("Mail successfully sent to user with heart rate information");
					Notification.show("Mail successfully sent to user with heart rate information!");
				} catch (NumberFormatException e) {
					Notification.show("Complete the minutes field with number", Type.ERROR_MESSAGE);
				}
			} else {
				Notification.show(
						"Complete the required fields with a valid e-mail & number of minutes and choose category",
						Type.ERROR_MESSAGE);
			}
		});
	}

	/**
	 * @param continueProcess
	 * @param request
	 * @param authorizationCode
	 * @param submitCheckBoxButton
	 * @param multiCheckBox
	 * @return
	 */
	public boolean continueBuilder(Button continueProcess, VaadinRequest request, Button authorizationCode,
			Button submitCheckBoxButton, CheckBoxGroup<String> multiCheckBox) {

		String endpoint = request.getPathInfo();

		switch (endpoint) {
		case "/fitbitApp/dashboard":
			if (authorizationCode.isEnabled()) {
				Notification.show(
						"Complete with a valid clientId & clientSecret and receive the authorization code required",
						Type.ERROR_MESSAGE);
				return false;
			}
			break;
		case "/fitbitApp/userData":
			if (submitCheckBoxButton.isEnabled()) {
				Notification.show("Complete the required steps before", Type.ERROR_MESSAGE);
				return false;
			} else if (!multiCheckBox.getValue().contains("HeartRate data")) {
				Notification.show(
						"Heart Rate data aren't exist into database so you can't continue to email process. Thank you!");
				return false;
			}
			break;
		default:
			break;
		}
		return true;

	}

	/**
	 * @param exit
	 * @param content
	 * @return
	 */
	public void exitBuilder(Button exit, VerticalLayout content) {
		exit.setIcon(VaadinIcons.EXIT);
		exit.setCaption("Exit");
		exit.setWidth("150");
		exit.addClickListener(click -> {
			clearFieldsService.removeAll(content);
		});
	}

}
