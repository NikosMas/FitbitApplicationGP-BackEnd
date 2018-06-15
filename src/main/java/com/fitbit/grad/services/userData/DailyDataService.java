package com.fitbit.grad.services.userData;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service about requesting to Fitbit api for daily data (steps, floors, heart-rate, distance)
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class DailyDataService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
    private final Environment env;
    private final RequestsOperationsService requestsOperationsService;

    @Autowired
    public DailyDataService(Environment env, RequestsOperationsService requestsOperationsService) {
        this.env = env;
        this.requestsOperationsService = requestsOperationsService;
    }

    public void storeIntradayData() {

        try {
            if ((requestsOperationsService.dailyRequests(env.getProperty("fitbitApiUrls.distanceUrl"), CollectionEnum.A_DISTANCE_D.d()) &&
                    requestsOperationsService.dailyRequests(env.getProperty("fitbitApiUrls.stepsUrl"), CollectionEnum.A_STEPS_D.d()) &&
                    requestsOperationsService.dailyRequests(env.getProperty("fitbitApiUrls.floorsUrl"), CollectionEnum.A_FLOORS_D.d()) &&
                    requestsOperationsService.dailyRequests(env.getProperty("fitbitApiUrls.heartUrl"), CollectionEnum.A_HEART_D.d()))) {
                LOG.info("Today's data til now are successfully received!");
            } else {
                LOG.error("Something went wrong so daily data operations are not completed");
            }

        } catch (IOException e) {
            LOG.error("Something went wrong: ", e);
        }
    }

}
