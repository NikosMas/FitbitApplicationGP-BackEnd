package com.fitbit.grad.services.userData;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

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

    private final RequestsOperationsService requestsOperationsService;
    private final CalendarService calendarService;
    private final Environment env;

    @Autowired
    public SleepDataService(RequestsOperationsService requestsOperationsService, CalendarService calendarService,
                            Environment env) {
        this.requestsOperationsService = requestsOperationsService;
        this.calendarService = calendarService;
        this.env = env;
    }

    public boolean sleep(List<Map<String, String>> dates) {

        String p = calendarService.months(dates).stream().filter(month -> !dataRetriever(month)).findFirst()
                .orElse(null);

        return null == p;
    }

    private boolean dataRetriever(String month) {
        return requestsOperationsService.requests(env.getProperty("fitbitApiUrls.timeInBedUrl"), month, CollectionEnum.S_TIME_IN_BED.d(), TIME_IN_BED)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.minutesAsleepUrl"), month, CollectionEnum.S_MINUTES_ASLEEP.d(), MINUTES_ASLEEP)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.minutesAwakeUrl"), month, CollectionEnum.S_MINUTES_AWAKE.d(), MINUTES_AWAKE)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.afterWakeUpUrl"), month, CollectionEnum.S_MINUTES_AFTER_WAKE_UP.d(), MINUTES_AFTER_WAKE_UP)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.efficiencyUrl"), month, CollectionEnum.S_EFFICIENCY.d(), EFFICIENCY)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.toFallAsleepUrl"), month, CollectionEnum.S_MINUTES_TO_FALL_ASLEEP.d(), MINUTES_TO_FALL_ASLEEP);
    }

}
