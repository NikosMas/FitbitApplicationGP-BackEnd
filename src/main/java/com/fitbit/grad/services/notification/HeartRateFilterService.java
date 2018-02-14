package com.fitbit.grad.services.notification;

import com.fitbit.grad.config.MailInfoProperties;
import com.fitbit.grad.controller.tabs.HeartRateNotificationTab;
import com.fitbit.grad.models.HeartRateCategoryEnum;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.repository.HeartRateZoneRepository;
import com.fitbit.grad.services.builders.ClearAllBuilderService;
import com.fitbit.grad.services.document.CreatePdfToolsService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service about filtering heart rate data from database according to info given at {@link HeartRateNotificationTab}
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartRateFilterService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    private final MailInfoProperties properties;
    private final HeartRateZoneRepository heartRepository;
    private final CreatePdfToolsService createPdfToolsService;
    private final ClearAllBuilderService clearFieldsService;
    private final HeartRateNotificationService sendMailService;

    @Autowired
    public HeartRateFilterService(MailInfoProperties properties, HeartRateZoneRepository heartRepository, CreatePdfToolsService createPdfToolsService, ClearAllBuilderService clearFieldsService, HeartRateNotificationService sendMailService) {
        this.properties = properties;
        this.heartRepository = heartRepository;
        this.createPdfToolsService = createPdfToolsService;
        this.clearFieldsService = clearFieldsService;
        this.sendMailService = sendMailService;
    }

    public void heartRateSelect(String mail, Long minutes, HeartRateCategoryEnum category, VerticalLayout content) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(properties.getFileName()));
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            document.open();

            if (heartRepository.findByMinutesGreaterThanAndNameIs(minutes, category.d()).count() == 0) {
                Chunk doc = new Chunk("There are not exist heart-rate data with that parameters.\n Thank you!!", font);
                document.add(doc);
            } else {
                Stream categories = Stream.of("Date", "Heart-Rate category", "Minutes", "Minimum heart-rate", "Maximum heart-rate", "Calories out");
                PdfPTable tableHeartRate = new PdfPTable(6);
                tableHeartRate.setSpacingAfter(70);
                tableHeartRate.setSpacingBefore(50);
                createPdfToolsService.addTable(tableHeartRate, categories);
                List<HeartRateValue> heartRateValueList = heartRepository.findByMinutesGreaterThanAndNameIs(minutes, category.d()).collect(Collectors.toList());
                createPdfToolsService.addRowsHeartRate(tableHeartRate, heartRateValueList);
                document.add(tableHeartRate);
            }

            document.addTitle("Heart-rate data");
            document.addAuthor("Fitbit Application");
            document.close();

            HeartRateValue heartRateZone = heartRepository.findDistinctByName(category.d());
            Long min = heartRateZone.getMin();
            Long max = heartRateZone.getMax();
            sendMailService.email(mail, minutes, category, min, max);

        } catch (MessagingException | IOException | DocumentException e) {
            LOG.error("Something went wrong: ", e);
            clearFieldsService.tryLater(content);
        }
    }
}
