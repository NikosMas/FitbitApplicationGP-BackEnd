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
import com.grad.services.collections.CreateCollectionsService;
import com.grad.services.mail.FitbitHeartCheckPeakService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ButtonsBuilderService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	List<Map<String, String>> dates = new ArrayList<>();

	@Autowired
	private FitbitHeartCheckPeakService heartPeakService;

	@Autowired
	private CreateCollectionsService collectionsService;

	@Autowired
	private AuthCodeRequestService codeService;

	@Autowired
	private ClearAllService clearFieldsService;

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
			collections.setEnabled(false);
			float current = bar.getValue();
			collectionsService.collectionsCreate();
			if (current < 1.0f)
				bar.setValue(current + 0.25f);
			LOG.info("Collections created successfully into Mongo database");
			Notification.show("Collections created successfully!");
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
			if (current > 0.2 && current < 1.0f) {
				if (!(clientId.isEmpty() || clientSecret.isEmpty())) {
					redisTemplate.opsForValue().set("Client-id", clientId.getValue());
					redisTemplate.opsForValue().set("Client-secret", clientSecret.getValue());
					authorizationCode.setEnabled(false);
					codeService.codeRequest();
					bar.setValue(current + 0.25f);
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
	 * @param heartRateMail
	 * @param bar
	 * @param mail
	 * @param heartRate
	 * @param multiCheckBox
	 * @return
	 */
	public Button heartRateMailBuilder(Button heartRateMail, ProgressBar bar, TextField mail, TextField heartRate,
			CheckBoxGroup<String> multiCheckBox) {
		heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
		heartRateMail.setCaption("Submit");
		heartRateMail.setWidth("150");
		heartRateMail.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.75f && current < 1.0f) {
				if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty() && mail.getValue().contains("@")) {
					try {
						heartPeakService.heartRateSelect(mail.getValue(), Long.valueOf(heartRate.getValue()));
						bar.setValue(current + 0.25f);
						heartRateMail.setEnabled(false);
						LOG.info("Mail successfully sent to user with heart rate information");
						Notification.show("Mail successfully sent to user with heart rate information!");
					} catch (NumberFormatException e) {
						Notification.show("Complete the minutes field with number", Type.ERROR_MESSAGE);
					}
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

	/**
	 * @param complete
	 * @param bar
	 * @param dateFields
	 * @param textFields
	 * @param buttons
	 * @param multiCheckBox
	 * @return
	 */
	public Button completeBuilder(Button complete, ProgressBar bar, List<DateField> dateFields,
			List<TextField> textFields, List<Button> buttons, CheckBoxGroup<String> multiCheckBox) {
		complete.setIcon(VaadinIcons.FILE_REFRESH);
		complete.setCaption("Try again");
		complete.setWidth("150");
		complete.addClickListener(click -> {
			float current = bar.getValue();
			if (current == 1.0f) {
				clearFieldsService.clearAll(dateFields, textFields, buttons, multiCheckBox, bar);
			} else {
				Notification.show("The process is not completed.", Type.ERROR_MESSAGE);
			}
		});

		return complete;
	}

	/**
	 * @param exit
	 * @param content
	 * @return
	 */
	public Button exitBuilder(Button exit, VerticalLayout content) {
		exit.setIcon(VaadinIcons.EXIT);
		exit.setCaption("Exit");
		exit.setWidth("150");
		exit.addClickListener(click -> {
			clearFieldsService.removeAll(content);
		});

		return exit;
	}
}
