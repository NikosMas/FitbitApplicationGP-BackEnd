package com.grad.heart;

import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Heart-Rate repository 
 * 
 * @author nikos_mas
 *
 */

public interface FitbitHeartZoneRepo extends MongoRepository<FitbitHeartRate, String> {

	public Stream<FitbitHeartRate> findByMinutesGreaterThanAndNameIs(long minutes, String name);
	
}
