package com.fitbit.grad.controller.tabs;

import com.fitbit.grad.models.HeartRateCategoryEnum;
import com.fitbit.grad.services.builders.ButtonsBuilderService;
import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.builders.FieldsBuilderService;
import com.fitbit.grad.services.builders.ToolsBuilderService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * controller at /fitbitApp/heartRateFilter waiting the user to fill the form
 * about heart rate & mail info
 *
 * @author nikos_mas, alex_kak
 */

public class HeartRateNotificationTab {

    @Title("Heart Rate Notification")
    @SpringUI(path = "fitbitApp/heartRateNotification")
    public static class VaadinUI extends UI {

        private static final long serialVersionUID = 1L;

        private final FieldsBuilderService fieldsService;
        private final ButtonsBuilderService buttonsService;
        private final ToolsBuilderService toolsService;
        private final ContentBuilderService contentService;

        @Autowired
        public VaadinUI(FieldsBuilderService fieldsService, ButtonsBuilderService buttonsService, ToolsBuilderService toolsService, ContentBuilderService contentService) {
            this.fieldsService = fieldsService;
            this.buttonsService = buttonsService;
            this.toolsService = toolsService;
            this.contentService = contentService;
        }

        @Override
        public void init(VaadinRequest request) {
            VerticalLayout content = new VerticalLayout();
            setContent(content);
            setResponsive(true);

            Image image = new Image();
            image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

            ComboBox<HeartRateCategoryEnum> select = new ComboBox<>();
            toolsService.comboBoxBuilder(select);

            TextField mail = new TextField();
            fieldsService.mailBuilder(mail);

            TextField heartRate = new TextField();
            fieldsService.heartRateBuilder(heartRate);

            Button skip = new Button();
            skip.setIcon(VaadinIcons.ARROW_FORWARD);
            skip.setCaption("skip");
            skip.setWidth("150");
            skip.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
            });

            Button heartRateMail = new Button();
            buttonsService.heartRateMailBuilder(skip, heartRateMail, mail, heartRate, select, content);

            Button exit = new Button();
            exit.setIcon(VaadinIcons.ARROW_FORWARD);
            exit.setCaption("exit");
            exit.setWidth("150");
            exit.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
            });

            contentService.heartRateFilterContentBuilder(content, image, select, heartRate, mail, heartRateMail, exit, skip);
        }
    }
}
