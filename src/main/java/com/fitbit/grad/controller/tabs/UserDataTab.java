package com.fitbit.grad.controller.tabs;

import java.io.File;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.collections.CollectionService;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import com.fitbit.grad.services.builders.ButtonsBuilderService;
import com.fitbit.grad.services.builders.CheckBoxBuilderService;
import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.builders.FieldsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;

import static com.vaadin.ui.Notification.*;

/**
 * controller at /fitbitApp/userData waiting the user to fill the form about
 * user data
 *
 * @author nikos_mas, alex_kak
 */

public class UserDataTab {

    @Title("User Data")
    @SpringUI(path = "fitbitApp/userData")
    public static class VaadinUI extends UI {

        private static final long serialVersionUID = 1L;

        @Autowired
        private FieldsBuilderService fieldsService;

        @Autowired
        private ButtonsBuilderService buttonsService;

        @Autowired
        private CheckBoxBuilderService checkBoxService;

        @Autowired
        private ContentBuilderService contentService;

        @Autowired
        private RedisTemplate<String, String> redisTemplate;

        @Autowired
        private MongoTemplate mongoTemplate;

        @Autowired
        private CollectionService collectionService;

        @Override
        public void init(VaadinRequest request) {
            VerticalLayout content = new VerticalLayout();
            setContent(content);
            setResponsive(true);

            Image image = new Image();
            image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

            CheckBoxGroup<String> multiCheckBox = new CheckBoxGroup<>();
            checkBoxService.checkBoxBuilder(multiCheckBox);

            DateField startDate = new DateField();
            fieldsService.dateBuilder(startDate);

            DateField endDate = new DateField();
            fieldsService.dateBuilder(endDate);

            TextField heartRate = new TextField();
            fieldsService.heartRateBuilder(heartRate);

            Button submitDates = new Button();
            checkBoxService.submitDates(submitDates, startDate, endDate, multiCheckBox);

            Button submitCheckBoxButton = new Button();
            checkBoxService.checkBoxButton(multiCheckBox, submitCheckBoxButton, content);

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button exit = new Button();
            exit.setIcon(VaadinIcons.ROTATE_LEFT);
            exit.setCaption("Exit");
            exit.setWidth("150");
            exit.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
            });

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button stepBackward = new Button();
            stepBackward.setIcon(VaadinIcons.ARROW_BACKWARD);
            stepBackward.setCaption("Back");
            stepBackward.setWidth("150");
            stepBackward.addClickListener(click -> {
                getPage().setLocation("dashboard");
                getSession().close();
                redisTemplate.delete("AuthorizationCode");
                collectionService.clearDatabase();
            });

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button stepForward = new Button();
            stepForward.setIcon(VaadinIcons.ARROW_FORWARD);
            stepForward.setCaption("Continue");
            stepForward.addClickListener(click -> {
                if (mongoTemplate.collectionExists(CollectionEnum.A_CALORIES.d())) {
                    if (buttonsService.continueBuilder(submitCheckBoxButton, multiCheckBox)) {
                        getPage().setLocation("heartRateNotification");
                        getSession().close();
                    } else {
                        getPage().setLocation("finalize");
                        getSession().close();
                    }
                }
                show("Complete the required steps before", Type.ERROR_MESSAGE);
            });

            contentService.userDataContentBuilder(content, image, multiCheckBox, startDate, endDate, heartRate,
                    submitDates, submitCheckBoxButton, exit, stepForward, stepBackward);
        }
    }
}
