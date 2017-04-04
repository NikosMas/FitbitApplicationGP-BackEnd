package com.grad.collections;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.grad.collections.CollectionEnum;

@Service
public class CreateCollectionsService {

	private static final List<CollectionEnum> collections = Arrays.asList(CollectionEnum.values());

	@Autowired
	private MongoTemplate mongoTemplate;

	public void collectionsCreate() {

		collections.stream().forEach(collectionName -> {

			if (mongoTemplate.collectionExists(collectionName.getDescription())) {
				mongoTemplate.dropCollection(collectionName.getDescription());
				mongoTemplate.createCollection(collectionName.getDescription());
			} else {
				mongoTemplate.createCollection(collectionName.getDescription());
			}
		});
	}

}
