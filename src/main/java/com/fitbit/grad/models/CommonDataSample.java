package com.fitbit.grad.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * model class required at saving service
 *
 * @author nikos_mas, alex_kak
 */

@Getter
@Setter
@NoArgsConstructor
public class CommonDataSample {

    private String dateTime;
    private String value;

}
