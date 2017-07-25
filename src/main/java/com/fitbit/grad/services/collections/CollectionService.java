package com.fitbit.grad.services.collections;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.fitbit.grad.domain.CollectionEnum;

/**
 * Service about creating Mongo collections to 'fitbit' database
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class CollectionService {

	private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values());

	@Autowired
	private MongoTemplate mongoTemplate;

	public void collectionsCreate() {
		
			collections.stream().forEach(collectionName -> {

				if (mongoTemplate.collectionExists(collectionName.d())) {
					mongoTemplate.dropCollection(collectionName.d());
					mongoTemplate.createCollection(collectionName.d());
				} else {
					mongoTemplate.createCollection(collectionName.d());
				}
			});
	}

}
