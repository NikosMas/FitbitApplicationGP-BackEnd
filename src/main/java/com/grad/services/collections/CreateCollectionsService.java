package com.grad.services.collections;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.grad.domain.CollectionEnum;

/**
 * @author nikos_mas
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CreateCollectionsService {

	private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values());

	@Autowired
	private MongoTemplate mongoTemplate;

	public void collectionsCreate() {
		
			collections.stream().forEach(collectionName -> {

				if (mongoTemplate.collectionExists(collectionName.description())) {
					mongoTemplate.dropCollection(collectionName.description());
					mongoTemplate.createCollection(collectionName.description());
				} else {
					mongoTemplate.createCollection(collectionName.description());
				}
			});
	}

}
