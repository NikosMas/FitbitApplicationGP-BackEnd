package com.grad.heart.repository;

import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.grad.domain.FitbitHeartRate;

/**
 * Repository about {@link FitbitHeartRate}
 * 
 * @author nikos_mas, alex_kak
 */

public interface FitbitHeartZoneRepo extends MongoRepository<FitbitHeartRate, String> {

	Stream<FitbitHeartRate> findByMinutesGreaterThanAndNameIs(long minutes, String name);
	
	FitbitHeartRate findDistinctByName(String name);

}
