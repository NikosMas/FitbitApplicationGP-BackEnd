package com.fitbit.grad.services.userData;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final FitbitApiUrlProperties urlsProp;
    private final CalendarService calendarService;
    private final RequestsOperationsService requestsOperationsService;

    @Autowired
    public ActivitiesDataService(FitbitApiUrlProperties urlsProp, CalendarService calendarService, RequestsOperationsService requestsOperationsService) {
        this.urlsProp = urlsProp;
        this.calendarService = calendarService;
        this.requestsOperationsService = requestsOperationsService;
    }

    public boolean activities(List<Map<String, String>> dates) {

        String p = calendarService.months(dates).stream().filter(pack -> !dataRetriever(pack)).findFirst()
                .orElse(null);

        return p == null;
    }

    private boolean dataRetriever(String month) {
        return requestsOperationsService.requests(urlsProp.getStepsUrl(), month, CollectionEnum.A_STEPS.d(), STEPS)
                && requestsOperationsService.requests(urlsProp.getFloorsUrl(), month, CollectionEnum.A_FLOORS.d(), FLOORS)
                && requestsOperationsService.requests(urlsProp.getDistanceUrl(), month, CollectionEnum.A_DISTANCE.d(), DISTANCE)
                && requestsOperationsService.requests(urlsProp.getCaloriesUrl(), month, CollectionEnum.A_CALORIES.d(), CALORIES);
    }
}
