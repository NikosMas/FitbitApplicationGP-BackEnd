package com.fitbit.grad.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * model class about collection 'heart_rate'
 *
 * @author nikos_mas, alex_kak
 */

@Document(collection = "heartRateValues")
@Getter
@Setter
@NoArgsConstructor
public class HeartRateValue {

    private String date;
    private String name;
    private Long minutes;
    private Long caloriesOut;
    private Long max;
    private Long min;

}
