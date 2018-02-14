package com.fitbit.grad.services.document;

import com.fitbit.grad.config.DownloadingProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.repository.HeartRateZoneRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author nikos_mas, alex_kak
 */

@Service
public class CreatePdfFileService {

    private final MongoTemplate mongoTemplate;
    private final HeartRateZoneRepository heartRateZoneRepository;
    private final DownloadingProperties downloadingProperties;
    private final CreatePdfToolsService createPdfToolsService;

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    @Autowired
    public CreatePdfFileService(MongoTemplate mongoTemplate, HeartRateZoneRepository heartRateZoneRepository, DownloadingProperties downloadingProperties, CreatePdfToolsService createPdfToolsService) {
        this.mongoTemplate = mongoTemplate;
        this.heartRateZoneRepository = heartRateZoneRepository;
        this.downloadingProperties = downloadingProperties;
        this.createPdfToolsService = createPdfToolsService;
    }

    /**
     * Check if data exist in database and add them to the pdf document
     */
    public void createDocumentWithUserData(List<String> parameters) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(downloadingProperties.getExportFileName()));
            Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 14, BaseColor.BLACK);
            document.open();

            if (parameters.contains("activities")) {
                List<CommonDataSample> stepsData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_STEPS.d());
                List<CommonDataSample> floorsData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_FLOORS.d());
                List<CommonDataSample> distanceData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_DISTANCE.d());
                List<CommonDataSample> caloriesData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_CALORIES.d());

                Chunk steps = new Chunk("Activity - Steps Table", font);
                Chunk floors = new Chunk("Activity - Floors Table", font);
                Chunk distance = new Chunk("Activity - Distance Table", font);
                Chunk calories = new Chunk("Activity - Calories Table", font);

                PdfPTable tableSteps = new PdfPTable(2);
                createPdfToolsService.addTable(tableSteps, Stream.of("Date", "Values"));
                tableSteps.setSpacingAfter(70);
                tableSteps.setSpacingBefore(50);
                createPdfToolsService.addRows(tableSteps, stepsData);

                PdfPTable tableFloors = new PdfPTable(2);
                createPdfToolsService.addTable(tableFloors, Stream.of("Date", "Values"));
                tableFloors.setSpacingAfter(70);
                tableFloors.setSpacingBefore(50);
                createPdfToolsService.addRows(tableFloors, floorsData);

                PdfPTable tableDistance = new PdfPTable(2);
                createPdfToolsService.addTable(tableDistance, Stream.of("Date", "Values"));
                tableDistance.setSpacingAfter(70);
                tableDistance.setSpacingBefore(50);
                createPdfToolsService.addRows(tableDistance, distanceData);

                PdfPTable tableCalories = new PdfPTable(2);
                createPdfToolsService.addTable(tableCalories, Stream.of("Date", "Values"));
                tableCalories.setSpacingAfter(70);
                tableCalories.setSpacingBefore(50);
                createPdfToolsService.addRows(tableCalories, caloriesData);

                document.add(steps);
                document.add(tableSteps);
                document.add(floors);
                document.add(tableFloors);
                document.add(distance);
                document.add(tableDistance);
                document.add(calories);
                document.add(tableCalories);
            }

            if (parameters.contains("sleep")) {
                List<CommonDataSample> efficiencyData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_EFFICIENCY.d());
                List<CommonDataSample> afterWakeUpData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d());
                List<CommonDataSample> asleepData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_MINUTES_ASLEEP.d());
                List<CommonDataSample> awakeData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_MINUTES_AWAKE.d());
                List<CommonDataSample> toFallAsleepData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d());
                List<CommonDataSample> inBedData = mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_TIME_IN_BED.d());

                Chunk efficiency = new Chunk("Sleep - Efficiency Table", font);
                Chunk wakeUp = new Chunk("Sleep - Minutes wakeUp Table", font);
                Chunk asleep = new Chunk("Sleep - Minutes asleep Table", font);
                Chunk awake = new Chunk("Sleep - Minutes awake Table", font);
                Chunk toFallAsleep = new Chunk("Sleep - Minutes to fall asleep Table", font);
                Chunk inBed = new Chunk("Sleep - Minutes in bed Table", font);

                PdfPTable tableEfficiency = new PdfPTable(2);
                createPdfToolsService.addTable(tableEfficiency, Stream.of("Date", "Values"));
                tableEfficiency.setSpacingAfter(70);
                tableEfficiency.setSpacingBefore(50);
                createPdfToolsService.addRows(tableEfficiency, efficiencyData);

                PdfPTable tableWakeUp = new PdfPTable(2);
                createPdfToolsService.addTable(tableWakeUp, Stream.of("Date", "Values"));
                tableWakeUp.setSpacingAfter(70);
                tableWakeUp.setSpacingBefore(50);
                createPdfToolsService.addRows(tableWakeUp, afterWakeUpData);

                PdfPTable tableAsleep = new PdfPTable(2);
                createPdfToolsService.addTable(tableAsleep, Stream.of("Date", "Values"));
                tableAsleep.setSpacingAfter(70);
                tableAsleep.setSpacingBefore(50);
                createPdfToolsService.addRows(tableAsleep, asleepData);

                PdfPTable tableAwake = new PdfPTable(2);
                createPdfToolsService.addTable(tableAwake, Stream.of("Date", "Values"));
                tableAwake.setSpacingAfter(70);
                tableAwake.setSpacingBefore(50);
                createPdfToolsService.addRows(tableAwake, awakeData);

                PdfPTable tableToFallAsleep = new PdfPTable(2);
                createPdfToolsService.addTable(tableToFallAsleep, Stream.of("Date", "Values"));
                tableToFallAsleep.setSpacingAfter(70);
                tableToFallAsleep.setSpacingBefore(50);
                createPdfToolsService.addRows(tableToFallAsleep, toFallAsleepData);

                PdfPTable tableInBed = new PdfPTable(2);
                createPdfToolsService.addTable(tableInBed, Stream.of("Date", "Values"));
                tableInBed.setSpacingAfter(70);
                tableInBed.setSpacingBefore(50);
                createPdfToolsService.addRows(tableInBed, inBedData);

                document.add(efficiency);
                document.add(tableEfficiency);
                document.add(asleep);
                document.add(tableAsleep);
                document.add(awake);
                document.add(tableAwake);
                document.add(toFallAsleep);
                document.add(tableToFallAsleep);
                document.add(inBed);
                document.add(tableInBed);
                document.add(wakeUp);
                document.add(tableWakeUp);
            }

            if (parameters.contains("heart")) {
                List<HeartRateValue> heartRateValues = heartRateZoneRepository.findAll();

                Chunk heart = new Chunk("Heart rate information Table", font);

                Stream categories = Stream.of("Date", "Heart-Rate category", "Minutes", "Minimum heart-rate", "Maximum heart-rate", "Calories out");

                PdfPTable tableHeartRate = new PdfPTable(6);
                createPdfToolsService.addTable(tableHeartRate, categories);
                tableHeartRate.setSpacingAfter(70);
                tableHeartRate.setSpacingBefore(50);
                createPdfToolsService.addRowsHeartRate(tableHeartRate, heartRateValues);

                document.add(heart);
                document.add(tableHeartRate);
            }

            document.addAuthor("Fitbit Application");
            document.addTitle("User-data information document");
            document.addCreationDate();

            document.close();

        } catch (DocumentException | IOException e) {
            LOG.error("Something went wrong: ", e);
        }
    }

}
