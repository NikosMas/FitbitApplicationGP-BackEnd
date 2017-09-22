package com.fitbit.grad.controller.tabs;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fitbit.grad.config.DownloadingProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.services.builders.ButtonsBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.collections.CollectionService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Notification.Type;

import static com.vaadin.ui.Notification.*;

/**
 * controller at /fitbitApp/finalize where user can restart the process
 *
 * @author nikos_mas, alex_kak
 */

public class FinalizeTab {

    @Title("Fitbit Application")
    @SpringUI(path = "fitbitApp/finalize")
    public static class VaadinUI extends UI {

        private static final long serialVersionUID = 1L;

        @Autowired
        private ContentBuilderService contentService;

        @Autowired
        private ButtonsBuilderService buttonsBuilderService;

        @Autowired
        private CollectionService collectionsService;

        @Autowired
        private RedisTemplate<String, String> redisTemplate;

        @Override
        public void init(VaadinRequest request) {
            VerticalLayout content = new VerticalLayout();
            setContent(content);
            setResponsive(true);

            Image image = new Image();
            image.setSource(new FileResource(new File("src/main/resources/images/ThankYou.png")));

            RadioButtonGroup<String> group = new RadioButtonGroup<>();
            if (redisTemplate.hasKey("AuthorizationCode")) {
                group.setEnabled(true);
            } else {
                group.setEnabled(false);
            }
            group.setItems("Same user", "Another user");
            group.setCaption("Choose the user you want");

            Button download = new Button();
            buttonsBuilderService.downloadBuilder(download);

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button platform = new Button();
            platform.setIcon(VaadinIcons.ARROW_FORWARD);
            platform.setCaption("Go To Platform");
            platform.setWidth("150");
            platform.addClickListener(click -> {
                getPage().setLocation("");
                getSession().close();
            });

            // business part with redirection is here because of private {@link
            // Page} at {@link UI}
            Button restart = new Button();
            restart.setIcon(VaadinIcons.ROTATE_LEFT);
            restart.setCaption("Restart");
            restart.setWidth("150");
            restart.addClickListener(click -> {
                if (!group.isEnabled()) {
                    getPage().setLocation("dashboard");
                    getSession().close();
                } else {
                    if (group.isEmpty()) {
                        show("Chose one of the options to continue", Type.ERROR_MESSAGE);
                    } else {
                        if (group.getValue().equals("Same user")) {
                            collectionsService.collectionsCreate();
                            getPage().setLocation("userData");
                            getSession().close();
                        } else {
                            redisTemplate.delete("AuthorizationCode");
                            getPage().setLocation("dashboard");
                            getSession().close();
                        }
                    }
                }
            });

            contentService.finalizeContentBuilder(content, image, group, restart, download, platform);
        }
    }
}
