package com.grad.services.collections;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.grad.domain.CollectionEnum;

/**
 * Service about creating Mongo collections to 'fitbit' database
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class CreateCollectionsService {

	private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values());

	@Autowired
	private MongoTemplate mongoTemplate;

	public void collectionsCreate() {
		
			collections.stream().forEach(collectionName -> {

				if (mongoTemplate.collectionExists(collectionName.desc())) {
					mongoTemplate.dropCollection(collectionName.desc());
					mongoTemplate.createCollection(collectionName.desc());
				} else {
					mongoTemplate.createCollection(collectionName.desc());
				}
			});
	}

}
