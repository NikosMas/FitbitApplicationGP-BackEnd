package com.fitbit.grad.controller;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.services.document.CreatePdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * rest controller on localhost to download pdf document
 *
 * @author nikos_mas, alex_kak
 */

@RestController
public class DocumentController {

    private final CreatePdfFileService createPdfFileService;
    private final Environment env;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public DocumentController(CreatePdfFileService createPdfFileService, Environment env,
                              MongoTemplate mongoTemplate) {
        this.createPdfFileService = createPdfFileService;
        this.env = env;
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(value = "fitbitApp/export")
    public ResponseEntity<Resource> getFile() throws IOException {

        List<String> parameters = new ArrayList<>();

        if (!mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.A_STEPS.d()).isEmpty())
            parameters.add("activities");

        if (!mongoTemplate.findAll(CommonDataSample.class, CollectionEnum.S_TIME_IN_BED.d()).isEmpty())
            parameters.add("sleep");

        if (!mongoTemplate.findAll(HeartRateValue.class, CollectionEnum.FILTERD_A_HEART.d()).isEmpty())
            parameters.add("heart");

        createPdfFileService.createDocumentWithUserData(parameters);
        File file = new File(env.getProperty("downloadProps.exportFileName"));
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header("Content-Disposition", "attachment; filename=" + env.getProperty("downloadProps.exportFileName"))
                .body(resource);
    }

}
