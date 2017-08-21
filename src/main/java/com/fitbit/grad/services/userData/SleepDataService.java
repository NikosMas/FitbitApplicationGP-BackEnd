package com.fitbit.grad.services.userData;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Service about requesting to fitbit api for sleep data
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class SleepDataService {

    // filtered field from response
    private static final String EFFICIENCY = "sleep-efficiency";
    private static final String MINUTES_TO_FALL_ASLEEP = "sleep-minutesToFallAsleep";
    private static final String MINUTES_AFTER_WAKE_UP = "sleep-minutesAfterWakeup";
    private static final String MINUTES_AWAKE = "sleep-minutesAwake";
    private static final String MINUTES_ASLEEP = "sleep-minutesAsleep";
    private static final String TIME_IN_BED = "sleep-timeInBed";

    @Autowired
    private RequestsOperationsService requestsOperationsService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private FitbitApiUrlProperties urlsProp;

    public boolean sleep(List<Map<String, String>> dates) {

        String p = calendarService.months(dates).stream().filter(month -> dataRetriever(month) == false).findFirst()
                .orElse(null);

        return (p == null) ? true : false;
    }

    private boolean dataRetriever(String month) {

        return (requestsOperationsService.requests(urlsProp.getTimeInBedUrl(), month, CollectionEnum.S_TIME_IN_BED.d(), TIME_IN_BED)
                && requestsOperationsService.requests(urlsProp.getMinutesAsleepUrl(), month, CollectionEnum.S_MINUTES_ASLEEP.d(), MINUTES_ASLEEP)
                && requestsOperationsService.requests(urlsProp.getMinutesAwakeUrl(), month, CollectionEnum.S_MINUTES_AWAKE.d(), MINUTES_AWAKE)
                && requestsOperationsService.requests(urlsProp.getAfterWakeUpUrl(), month, CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d(), MINUTES_AFTER_WAKE_UP)
                && requestsOperationsService.requests(urlsProp.getEfficiencyUrl(), month, CollectionEnum.S_EFFICIENCY.d(), EFFICIENCY)
                && requestsOperationsService.requests(urlsProp.getToFallAsleepUrl(), month, CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d(), MINUTES_TO_FALL_ASLEEP))
                ? true
                : false;
    }

}
