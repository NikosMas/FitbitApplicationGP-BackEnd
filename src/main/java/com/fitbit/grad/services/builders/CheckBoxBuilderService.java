package com.fitbit.grad.services.builders;

import com.fitbit.grad.services.calendar.CalendarService;
import com.fitbit.grad.services.collections.CollectionService;
import com.fitbit.grad.services.userData.ActivitiesDataService;
import com.fitbit.grad.services.userData.HeartDataService;
import com.fitbit.grad.services.userData.OtherDataService;
import com.fitbit.grad.services.userData.SleepDataService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vaadin.ui.Notification.show;

/**
 * Service about Vaadin checkbox building
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class CheckBoxBuilderService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
    private List<Map<String, String>> dates = new ArrayList<>();

    private final ActivitiesDataService activitiesService;
    private final HeartDataService heartService;
    private final OtherDataService otherService;
    private final SleepDataService sleepService;
    private final CalendarService calendarService;
    private final ClearAllBuilderService clearFieldsService;
    private final CollectionService collectionService;

    @Autowired
    public CheckBoxBuilderService(ActivitiesDataService activitiesService, HeartDataService heartService, OtherDataService otherService, SleepDataService sleepService, CalendarService calendarService, ClearAllBuilderService clearFieldsService, CollectionService collectionService) {
        this.activitiesService = activitiesService;
        this.heartService = heartService;
        this.otherService = otherService;
        this.sleepService = sleepService;
        this.calendarService = calendarService;
        this.clearFieldsService = clearFieldsService;
        this.collectionService = collectionService;
    }

    public void checkBoxButton(CheckBoxGroup<String> multiCheckBox, Button submitCheckBoxButton, VerticalLayout content) {

        submitCheckBoxButton.setIcon(VaadinIcons.CHECK_CIRCLE);
        submitCheckBoxButton.setCaption("Submit");
        submitCheckBoxButton.setWidth("150");
        submitCheckBoxButton.addClickListener(click -> {
            if (!multiCheckBox.isEmpty()) {
                submitCheckBoxButton.setEnabled(false);
                collectionService.collectionsCreate();
                for (String check : multiCheckBox.getSelectedItems()) {
                    switch (check) {
                        case "Sleep data":
                            if (sleepService.sleep(dates)) {
                                LOG.info("Sleep data received and stored to database");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        case "Profile data":
                            if (otherService.profile()) {
                                LOG.info("Profile data received and stored to database");
                                show("User data stored successfully!");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        case "Activities data":
                            if (activitiesService.activities(dates)) {
                                LOG.info("Activities data received and stored to database");
                                show("User data stored successfully!");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        case "Lifetime activities data":
                            if (otherService.lifetime()) {
                                LOG.info("Lifetime data received and stored to database");
                                show("User data stored successfully!");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        case "Frequent activities data":
                            if (otherService.frequence()) {
                                LOG.info("Frequence data received and stored to database");
                                show("User data stored successfully!");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        case "HeartRate data":
                            if (heartService.filterHeartRateValues(dates)) {
                                LOG.info("Heart rate data received and stored to database");
                                show("User data stored successfully!");
                            } else {
                                clearFieldsService.tryLater(content);
                            }
                            break;
                        default:
                            break;
                    }
                }

            } else {
                show("You missed some steps before or you didn't select anything", Type.ERROR_MESSAGE);
            }
        });
    }

    public void submitDates(Button submitDates, DateField startDate, DateField endDate,
                            CheckBoxGroup<String> multiCheckBox) {
        submitDates.setIcon(VaadinIcons.CHECK_CIRCLE);
        submitDates.setCaption("Submit");
        submitDates.setWidth("150");
        submitDates.addClickListener(click -> {
            if (!startDate.isEmpty() && !endDate.isEmpty() && startDate.getValue().isBefore(endDate.getValue())) {
                dates = calendarService.getDates(startDate.getValue(), endDate.getValue());
                submitDates.setEnabled(false);
                startDate.setEnabled(false);
                endDate.setEnabled(false);
                multiCheckBox.setEnabled(true);
            } else {
                show("You missed some steps before or dates given are invalid", Type.ERROR_MESSAGE);
            }
        });
    }

    public void checkBoxBuilder(CheckBoxGroup<String> multiCheckBox) {

        multiCheckBox.setCaption("User data categories");
        multiCheckBox.setItems("Sleep data", "Profile data", "Activities data", "Lifetime activities data",
                "Frequent activities data", "HeartRate data");
        multiCheckBox.setEnabled(false);

    }

}
