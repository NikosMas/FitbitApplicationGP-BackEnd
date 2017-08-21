package com.fitbit.grad.services.userData;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DailyDataService {

    @Autowired
    private FitbitApiUrlProperties urlsProp;

    @Autowired
    private RequestsOperationsService requestsOperationsService;

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    public void storeIntradayData() {

        try {
            if (requestsOperationsService.dailyRequests(urlsProp.getDistanceUrl(), CollectionEnum.A_DISTANCE_D.d()) &&
                    requestsOperationsService.dailyRequests(urlsProp.getStepsUrl(), CollectionEnum.A_STEPS_D.d()) &&
                    requestsOperationsService.dailyRequests(urlsProp.getFloorsUrl(), CollectionEnum.A_FLOORS_D.d()) &&
                    requestsOperationsService.dailyRequests(urlsProp.getHeartUrl(), CollectionEnum.A_HEART_D.d()))
                LOG.info("Today's data til now are successfully received!");

            LOG.error("Something went wrong so daily data operations are not completed");
        } catch (IOException e) {
            LOG.error("Something went wrong: ", e);
        }
    }

}
