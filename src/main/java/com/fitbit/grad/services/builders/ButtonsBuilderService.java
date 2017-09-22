package com.fitbit.grad.services.builders;

import com.fitbit.grad.config.DownloadingProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fitbit.grad.models.HeartRateCategoryEnum;
import com.fitbit.grad.services.authRequests.AuthCodeRequestService;
import com.fitbit.grad.services.collections.CollectionService;
import com.fitbit.grad.services.notification.HeartRateFilterService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.io.IOException;

import static com.vaadin.ui.Notification.*;

/**
 * Service about Vaadin buttons building
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class ButtonsBuilderService {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private DownloadingProperties downloadingProperties;

    @Autowired
    private HeartRateFilterService heartRateFilterService;

    @Autowired
    private CollectionService collectionsService;

    @Autowired
    private AuthCodeRequestService codeService;

    @Autowired
    private ClearAllBuilderService clearFieldsService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
    private static String OS = System.getProperty("os.name");

    /**
     * @param authorizationCode
     * @param clientId
     * @param clientSecret
     * @param exit
     * @return
     */
    public void authorizationBuilder(Button authorizationCode, TextField clientId, TextField clientSecret, Button exit) {
        authorizationCode.setIcon(VaadinIcons.CHECK_CIRCLE);
        authorizationCode.setCaption("Submit");
        authorizationCode.setWidth("150");
        authorizationCode.addClickListener(click -> {
            if (!clientId.isEmpty() && !clientSecret.isEmpty()) {
                redisTemplate.opsForValue().set("Client-id", clientId.getValue());
                redisTemplate.opsForValue().set("Client-secret", clientSecret.getValue());
                authorizationCode.setEnabled(false);
                clientId.setEnabled(false);
                clientSecret.setEnabled(false);
                codeService.codeRequest();
                collectionsService.collectionsCreate();
            } else {
                show(
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
    public void heartRateMailBuilder(Button skip, Button heartRateMail, TextField mail, TextField heartRate,
                                     ComboBox<HeartRateCategoryEnum> select, VerticalLayout content) {
        heartRateMail.setIcon(VaadinIcons.CHECK_CIRCLE);
        heartRateMail.setCaption("Submit");
        heartRateMail.setWidth("150");
        heartRateMail.addClickListener(click -> {
            if (!mail.getValue().isEmpty() && !heartRate.getValue().isEmpty() && mail.getValue().contains("@")
                    && !select.isEmpty()) {
                try {
                    heartRateFilterService.heartRateSelect(mail.getValue(), Long.valueOf(heartRate.getValue()),
                            select.getValue(), content);
                    heartRateMail.setEnabled(false);
                    select.setEnabled(false);
                    mail.setEnabled(false);
                    heartRate.setEnabled(false);
                    skip.setEnabled(false);
                    LOG.info("Mail successfully sent to user with heart rate information");
                    show("Mail successfully sent to user with heart rate information!");
                } catch (NumberFormatException e) {
                    show("Complete the minutes field with number", Type.ERROR_MESSAGE);
                }
            } else {
                show(
                        "Complete the required fields with a valid e-mail & number of minutes and choose category",
                        Type.ERROR_MESSAGE);
            }
        });
    }

    /**
     * @param submitCheckBoxButton
     * @param multiCheckBox
     * @return
     */
    public boolean continueBuilder(Button submitCheckBoxButton, CheckBoxGroup<String> multiCheckBox) {

        if (submitCheckBoxButton.isEnabled())
            show("Complete the required steps before", Type.ERROR_MESSAGE);

        if (!multiCheckBox.getValue().contains("HeartRate data"))
            show("Heart Rate data aren't exist into database so you can't continue to email process");

        return true;
    }

    /**
     * @param download
     */
    public void downloadBuilder(Button download) {
        download.setIcon(VaadinIcons.DOWNLOAD);
        download.setCaption("Download");
        download.setWidth("150");
        download.addClickListener(click -> {
            if (mongoTemplate.collectionExists(CollectionEnum.A_STEPS.d())) {
                if (mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_STEPS.d()).isEmpty()
                        && mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_MINUTES_AWAKE.d()).isEmpty()
                        && mongoTemplate.findAll(HeartRateValue.class, CollectionEnum.FILTERD_A_HEART.d()).isEmpty())
                    show("No user data available for downloading", Type.ERROR_MESSAGE);
                else {
                    try {
                        if (OS.equalsIgnoreCase("linux")) {
                            Runtime.getRuntime().exec("xdg-open " + downloadingProperties.getExportUrl());
                        } else {
                            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + downloadingProperties.getExportUrl());
                        }
                    } catch (IOException e) {
                        LOG.error("Something went wrong: ", e);
                    }
                }
            }
            show("No user data available for downloading", Type.ERROR_MESSAGE);
        });
    }
}
