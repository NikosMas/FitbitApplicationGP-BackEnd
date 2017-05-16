package com.grad.heart.repository;

import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.grad.domain.FitbitHeartRate;

/**
 * @author nikos_mas
 */

public interface FitbitHeartZoneRepo extends MongoRepository<FitbitHeartRate, String> {

	Stream<FitbitHeartRate> findByMinutesGreaterThanAndNameIs(long minutes, String name);
	
	FitbitHeartRate findDistinctByName(String name);

}
