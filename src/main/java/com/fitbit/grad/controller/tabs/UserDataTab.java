package com.fitbit.grad.controller.tabs;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.builders.ButtonsBuilderService;
import com.fitbit.grad.services.builders.CheckBoxBuilderService;
import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.builders.FieldsBuilderService;
import com.fitbit.grad.services.collections.CollectionService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;

import static com.vaadin.ui.Notification.Type;
import static com.vaadin.ui.Notification.show;

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

        private final FieldsBuilderService fieldsService;
        private final ButtonsBuilderService buttonsService;
        private final CheckBoxBuilderService checkBoxService;
        private final ContentBuilderService contentService;
        private final RedisTemplate<String, String> redisTemplate;
        private final MongoTemplate mongoTemplate;
        private final CollectionService collectionService;

        @Autowired
        public VaadinUI(FieldsBuilderService fieldsService, ButtonsBuilderService buttonsService, CheckBoxBuilderService checkBoxService, ContentBuilderService contentService, RedisTemplate<String, String> redisTemplate, MongoTemplate mongoTemplate, CollectionService collectionService) {
            this.fieldsService = fieldsService;
            this.buttonsService = buttonsService;
            this.checkBoxService = checkBoxService;
            this.contentService = contentService;
            this.redisTemplate = redisTemplate;
            this.mongoTemplate = mongoTemplate;
            this.collectionService = collectionService;
        }

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

            Button exit = new Button();
            exit.setIcon(VaadinIcons.ROTATE_LEFT);
            exit.setCaption("Exit");
            exit.setWidth("150");
            exit.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
            });

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

            contentService.userDataContentBuilder(content, image, multiCheckBox, startDate, endDate,
                    submitDates, submitCheckBoxButton, exit, stepForward, stepBackward);
        }
    }
}
