package com.fitbit.grad.services.collections;

import com.fitbit.grad.models.CollectionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service about creating Mongo collections to 'fitbit' database
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class CollectionService {

    private final MongoTemplate mongoTemplate;
    private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values()).subList(0, 24);

    @Autowired
    public CollectionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void collectionsCreate() {


        collections.forEach(collectionName -> {
            if (mongoTemplate.collectionExists(collectionName.d())) {
                mongoTemplate.dropCollection(collectionName.d());
                mongoTemplate.createCollection(collectionName.d());
            } else {
                mongoTemplate.createCollection(collectionName.d());
            }
        });
    }

    public void clearDatabase() {
        collections.stream()
                .filter(collectionName -> mongoTemplate.collectionExists(collectionName.d()))
                .forEach(collectionName -> mongoTemplate.dropCollection(collectionName.d()));
    }
}
