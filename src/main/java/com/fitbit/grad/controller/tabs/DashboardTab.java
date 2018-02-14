package com.fitbit.grad.controller.tabs;

import com.fitbit.grad.services.builders.ButtonsBuilderService;
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

import java.io.File;

/**
 * controller at /fitbitApp/dashboard waiting the user to fill the form about
 * authorization info
 *
 * @author nikos_mas, alex_kak
 */

public class DashboardTab {

    @Title("Home")
    @SpringUI(path = "fitbitApp/dashboard")
    public static class VaadinUI extends UI {

        private static final long serialVersionUID = 1L;

        private final FieldsBuilderService fieldsService;
        private final ButtonsBuilderService buttonsService;
        private final ContentBuilderService contentService;
        private final CollectionService collectionService;

        @Autowired
        public VaadinUI(FieldsBuilderService fieldsService, ButtonsBuilderService buttonsService, ContentBuilderService contentService, CollectionService collectionService) {
            this.fieldsService = fieldsService;
            this.buttonsService = buttonsService;
            this.contentService = contentService;
            this.collectionService = collectionService;
        }

        @Override
        public void init(VaadinRequest request) {
            VerticalLayout content = new VerticalLayout();
            setContent(content);
            setResponsive(true);

            Image image = new Image();
            image.setSource(new FileResource(new File("src/main/resources/images/FitbitLogo.png")));

            TextField clientId = new TextField();
            fieldsService.clientIdBuilder(clientId);

            TextField clientSecret = new TextField();
            fieldsService.clientSecretBuilder(clientSecret);

            Button exit = new Button();
            exit.setIcon(VaadinIcons.ROTATE_LEFT);
            exit.setCaption("Exit");
            exit.setWidth("150");
            exit.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
                collectionService.clearDatabase();
            });

            Button authorizationCode = new Button();
            buttonsService.authorizationBuilder(authorizationCode, clientId, clientSecret, exit);

            contentService.dashboardContentBuilder(content, image, clientId, clientSecret, authorizationCode, exit);
        }
    }

}
