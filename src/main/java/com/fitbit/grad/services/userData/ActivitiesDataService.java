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
 * Service about requesting to Fitbit api for activity data
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class ActivitiesDataService {

    // filtered field from response
    private static final String CALORIES = "activities-calories";
    private static final String DISTANCE = "activities-distance";
    private static final String FLOORS = "activities-floors";
    private static final String STEPS = "activities-steps";

    private final Environment env;
    private final CalendarService calendarService;
    private final RequestsOperationsService requestsOperationsService;

    @Autowired
    public ActivitiesDataService(Environment env, CalendarService calendarService, RequestsOperationsService requestsOperationsService) {
        this.env = env;
        this.calendarService = calendarService;
        this.requestsOperationsService = requestsOperationsService;
    }

    public boolean activities(List<Map<String, String>> dates) {

        String p = calendarService.months(dates).stream().filter(pack -> !dataRetriever(pack)).findFirst()
                .orElse(null);

        return p == null;
    }

    private boolean dataRetriever(String month) {
        return requestsOperationsService.requests(env.getProperty("fitbitApiUrls.stepsUrl"),
                month, CollectionEnum.A_STEPS.d(), STEPS)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.floorsUrl"),
                month, CollectionEnum.A_FLOORS.d(), FLOORS)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.distanceUrl"),
                month, CollectionEnum.A_DISTANCE.d(), DISTANCE)
                && requestsOperationsService.requests(env.getProperty("fitbitApiUrls.caloriesUrl"),
                month, CollectionEnum.A_CALORIES.d(), CALORIES);
    }
}
