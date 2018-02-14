package com.fitbit.grad.repository;

import com.fitbit.grad.models.HeartRateValue;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.stream.Stream;

/**
 * Repository about {@link HeartRateValue}
 *
 * @author nikos_mas, alex_kak
 */

public interface HeartRateZoneRepository extends MongoRepository<HeartRateValue, String> {

    Stream<HeartRateValue> findByMinutesGreaterThanAndNameIs(long minutes, String name);

    HeartRateValue findDistinctByName(String name);

}
