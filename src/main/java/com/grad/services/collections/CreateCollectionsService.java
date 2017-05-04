package com.grad.services.collections;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.grad.domain.CollectionEnum;

/**
 * @author nikos_mas
 */

@Service
public class CreateCollectionsService {

	private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values());

	@Autowired
	private MongoTemplate mongoTemplate;

	public boolean collectionsCreate() {
		
		if (mongoTemplate.getDb() != null) {
			collections.stream().forEach(collectionName -> {

				if (mongoTemplate.collectionExists(collectionName.getDescription())) {
					mongoTemplate.dropCollection(collectionName.getDescription());
					mongoTemplate.createCollection(collectionName.getDescription());
				} else {
					mongoTemplate.createCollection(collectionName.getDescription());
				}
			});
			return true;
		}
		return false;
	}

}
