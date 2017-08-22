package com.fitbit.grad.services.notification;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import javax.mail.MessagingException;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitbit.grad.config.MailInfoProperties;
import com.fitbit.grad.controller.tabs.HeartRateNotificationTab;
import com.fitbit.grad.models.HeartRateCategoryEnum;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.repository.HeartRateZoneRepository;
import com.fitbit.grad.services.builders.ClearAllBuilderService;
import com.vaadin.ui.VerticalLayout;

/**
 * Service about filtering heart rate data from database according to info given at {@link HeartRateNotificationTab}
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartRateFilterService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    @Autowired
    private MailInfoProperties properties;

    @Autowired
    private HeartRateZoneRepository heartRepository;

    @Autowired
    private ClearAllBuilderService clearFieldsService;

    @Autowired
    private HeartRateNotificationService sendMailService;

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
                PdfPTable tableHeartRate = new PdfPTable(6);
                tableHeartRate.setSpacingAfter(70);
                tableHeartRate.setSpacingBefore(50);

                Stream.of("Date", "Heart-Rate category", "Minutes", "Minimum heart-rate", "Maximum heart-rate", "Calories out")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(2);
                            header.setPhrase(new Phrase(columnTitle));
                            tableHeartRate.addCell(header);
                        });

                heartRepository.findByMinutesGreaterThanAndNameIs(minutes, category.d()).forEach(d -> {
                    tableHeartRate.addCell(d.getDate());
                    tableHeartRate.addCell(d.getName());
                    tableHeartRate.addCell(String.valueOf(d.getMinutes()));
                    tableHeartRate.addCell(String.valueOf(d.getMin()));
                    tableHeartRate.addCell(String.valueOf(d.getMax()));
                    tableHeartRate.addCell(String.valueOf(d.getCaloriesOut()));
                });
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
