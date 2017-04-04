package com.grad.heart.repository;

import java.util.stream.Stream;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grad.heart.domain.FitbitHeartRate;

/**
 * Heart-Rate repository
 * 
 * @author nikos_mas
 *
 */

public interface FitbitHeartZoneRepo extends MongoRepository<FitbitHeartRate, String> {

	//@Query("select distinct dates as date, minutes as minutes from FitbitHeartRate f where f.minutes > :minutes and f.name like :name ")
	public Stream<FitbitHeartRate> findByMinutesGreaterThanAndNameIs(long minutes, String name);

}
