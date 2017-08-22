package com.fitbit.grad.controller;

import com.fitbit.grad.config.DownloadingProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.models.CommonDataSample;
import com.fitbit.grad.models.HeartRateValue;
import com.fitbit.grad.services.document.CreatePdfFileService;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
public class DocumentController {

    @Autowired
    private CreatePdfFileService createPdfFileService;

    @Autowired
    private DownloadingProperties downloadingProperties;

    @Autowired
    private MongoTemplate mongoTemplate;

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
        File file = new File(downloadingProperties.getExportFileName());
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .header("Content-Disposition", "attachment; filename=" + downloadingProperties.getExportFileName())
                .body(resource);
    }

}
