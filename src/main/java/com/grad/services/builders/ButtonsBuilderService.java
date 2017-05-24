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

import com.grad.domain.HeartRateCategory;
import com.grad.services.auth.AuthCodeRequestService;
import com.grad.services.collections.CreateCollectionsService;
import com.grad.services.mail.FitbitHeartCheckPeakService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

/**
 * @author nikosmas
 *
 */
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
	 * @return
	 */
	public void authorizationBuilder(Button authorizationCode, TextField clientId, TextField clientSecret) {
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
			ComboBox<HeartRateCategory> select, VerticalLayout content) {
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
	 * @param complete
	 * @param bar
	 * @param dateFields
	 * @param textFields
	 * @param buttons
	 * @param multiCheckBox
	 * @param select
	 */
	public void completeBuilder(Button complete, ProgressBar bar, List<DateField> dateFields,
			List<TextField> textFields, List<Button> buttons, CheckBoxGroup<String> multiCheckBox,
			ComboBox<HeartRateCategory> select) {
		complete.setIcon(VaadinIcons.FILE_REFRESH);
		complete.setCaption("Try again");
		complete.setWidth("150");
		complete.addClickListener(click -> {
			float current = bar.getValue();
			if (current == 1.0f) {
				clearFieldsService.clearAll(dateFields, textFields, buttons, multiCheckBox, bar, select);
			} else {
				Notification.show("The process is not completed.", Type.ERROR_MESSAGE);
			}
		});
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
