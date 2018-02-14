package com.fitbit.grad.controller.tabs;

import com.fitbit.grad.services.builders.ButtonsBuilderService;
import com.fitbit.grad.services.builders.ContentBuilderService;
import com.fitbit.grad.services.collections.CollectionService;
import com.vaadin.annotations.Title;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;

import static com.vaadin.ui.Notification.show;

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

        private final ContentBuilderService contentService;
        private final ButtonsBuilderService buttonsBuilderService;
        private final CollectionService collectionsService;
        private final RedisTemplate<String, String> redisTemplate;

        @Autowired
        public VaadinUI(ContentBuilderService contentService, ButtonsBuilderService buttonsBuilderService, CollectionService collectionsService, RedisTemplate<String, String> redisTemplate) {
            this.contentService = contentService;
            this.buttonsBuilderService = buttonsBuilderService;
            this.collectionsService = collectionsService;
            this.redisTemplate = redisTemplate;
        }

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
                        collectionsService.clearDatabase();
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

            contentService.finalizeContentBuilder(content, image, group, restart, download);
        }
    }
}
