package com.grad.services.builders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.grad.services.calendar.CalendarService;
import com.grad.services.data.ActivitiesDataService;
import com.grad.services.data.HeartDataService;
import com.grad.services.data.OtherDataService;
import com.grad.services.data.SleepDataService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CheckBoxBuilderService {

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	List<Map<String, String>> dates = new ArrayList<>();

	@Autowired
	private ActivitiesDataService activitiesService;

	@Autowired
	private HeartDataService heartService;

	@Autowired
	private OtherDataService otherService;

	@Autowired
	private SleepDataService sleepService;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private ClearAllService clearFieldsService;

	/**
	 * @param multiCheckBox
	 * @param submitCheckBoxButton
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button checkBoxButton(CheckBoxGroup<String> multiCheckBox, Button submitCheckBoxButton, ProgressBar bar,
			DateField startDate, DateField endDate, Button heartRateMailBuilder, VerticalLayout content) {

		submitCheckBoxButton.setIcon(VaadinIcons.CHECK_CIRCLE);
		submitCheckBoxButton.setCaption("Submit");
		submitCheckBoxButton.setWidth("150");
		submitCheckBoxButton.addClickListener(click -> {
			float current = bar.getValue();
			if (current > 0.4f && current < 1.0f && !multiCheckBox.getSelectedItems().isEmpty()) {

				multiCheckBox.getSelectedItems().stream().forEach(check -> {

					switch (check) {
					case "Sleep data":
						if (sleepService.sleep(dates)) {
							LOG.info("Sleep data recieved and stored to database");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					case "Profile data":
						if (otherService.profile()) {
							LOG.info("Profile data recieved and stored to database");
							bar.setValue(current + 0.041666667f);
							Notification.show("User data stored successfully!");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					case "Activities data":
						if (activitiesService.activities(dates)) {
							LOG.info("Activities data recieved and stored to database");
							bar.setValue(current + 0.041666667f);
							Notification.show("User data stored successfully!");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					case "Lifetime activities data":
						if (otherService.lifetime()) {
							LOG.info("Lifetime data recieved and stored to database");
							bar.setValue(current + 0.041666667f);
							Notification.show("User data stored successfully!");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					case "Frequent activities data":
						if (otherService.frequence()) {
							LOG.info("Frequence data recieved and stored to database");
							bar.setValue(current + 0.041666667f);
							Notification.show("User data stored successfully!");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					case "HeartRate data":
						if (heartService.filterHeartRateValues(dates)) {
							LOG.info("Heart rate data recieved and stored to database");
							bar.setValue(current + 0.041666667f);
							Notification.show("User data stored successfully!");
						} else {
							Notification.show("Something went wrong! Please try later", Type.ERROR_MESSAGE);
							clearFieldsService.removeAll(content);
						}
						break;
					default:
						break;
					}
				});

				float cur = 0.75f;
				if (!multiCheckBox.isSelected("HeartRate data")) {
					heartRateMailBuilder.setEnabled(false);
					cur += 0.25f;
				}
				bar.setValue(current + (cur - current));
				submitCheckBoxButton.setEnabled(false);
				multiCheckBox.setEnabled(false);

			} else {
				Notification.show("You missed some steps before or you didn't select anything", Type.ERROR_MESSAGE);
			}
		});

		return submitCheckBoxButton;
	}

	/**
	 * @param submitDates
	 * @param bar
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Button submitDates(Button submitDates, ProgressBar bar, DateField startDate, DateField endDate,
			CheckBoxGroup<String> multiCheckBox) {
		submitDates.setIcon(VaadinIcons.CHECK_CIRCLE);
		submitDates.setCaption("Submit");
		submitDates.setWidth("150");
		submitDates.addClickListener(click -> {
			float current = bar.getValue();
			if (!startDate.isEmpty() && !endDate.isEmpty() && startDate.getValue().isBefore(endDate.getValue())
					&& current > 0.4f && current < 1.0f) {
				dates = calendarService.getDates(startDate.getValue(), endDate.getValue());
				submitDates.setEnabled(false);
				startDate.setEnabled(false);
				endDate.setEnabled(false);
				multiCheckBox.setEnabled(true);
			} else {
				Notification.show("You missed some steps before or dates given are invalid", Type.ERROR_MESSAGE);
			}
		});

		return submitDates;
	}

}
