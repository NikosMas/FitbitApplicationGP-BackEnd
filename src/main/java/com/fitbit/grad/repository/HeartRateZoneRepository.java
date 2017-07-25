package com.fitbit.grad.repository;

import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fitbit.grad.models.HeartRateValue;

/**
 * Repository about {@link HeartRateValue}
 * 
 * @author nikos_mas, alex_kak
 */

public interface HeartRateZoneRepository extends MongoRepository<HeartRateValue, String> {

	Stream<HeartRateValue> findByMinutesGreaterThanAndNameIs(long minutes, String name);
	
	HeartRateValue findDistinctByName(String name);

}
