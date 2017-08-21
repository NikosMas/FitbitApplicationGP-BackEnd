package com.fitbit.grad.controller.tabs;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.fitbit.grad.services.builders.ButtonsBuilderService;
import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.builders.FieldsBuilderService;
import com.fitbit.grad.services.userData.DailyDataService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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

        @Autowired
        private FieldsBuilderService fieldsService;

        @Autowired
        private ButtonsBuilderService buttonsService;

        @Autowired
        private ContentBuilderService contentService;

        @Autowired
        private DailyDataService dailyDataService;

        @Autowired
        private RedisTemplate<String, String> redisTemplate;

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

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button exit = new Button();
            exit.setIcon(VaadinIcons.ROTATE_LEFT);
            exit.setCaption("Exit");
            exit.setWidth("150");
            exit.setEnabled(false);
            exit.addClickListener(click -> {
                getPage().setLocation("finalize");
                getSession().close();
            });

            Button authorizationCode = new Button();
            buttonsService.authorizationBuilder(authorizationCode, clientId, clientSecret, exit);

            Button restart = new Button();
            restart.setIcon(VaadinIcons.ROTATE_LEFT);
            restart.setCaption("Restart");
            restart.setWidth("150");
            restart.addClickListener(click -> {
                getPage().reload();
            });

            Button stepForward = new Button();
            stepForward.setIcon(VaadinIcons.ARROW_FORWARD);
            stepForward.setCaption("Continue");
            stepForward.addClickListener(click -> {
                if (!redisTemplate.hasKey("AuthorizationCode") || !buttonsService.continueBuilder(request, authorizationCode, null, null)) {
                    Notification.show("Complete the authorization before continue", Type.ERROR_MESSAGE);
                } else {
                    dailyDataService.storeIntradayData();
                    getPage().setLocation("userData");
                    getSession().close();
                }
            });

            contentService.dashboardContentBuilder(content, image, clientId, clientSecret, authorizationCode, exit,
                    stepForward, restart);
        }
    }

}
