package com.fitbit.grad.services.userData;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service about requesting to fitbit api for profile, lifetime activities & frequent activities data
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class OtherDataService {

    // filtered field from response
    private static final String PROFILE_USER = "user";
    private static final String FREQUENCE_CATEGORIES = "categories";

    @Autowired
    private RequestsOperationsService requestsOperationsService;

    @Autowired
    private FitbitApiUrlProperties urlsProp;

    public boolean profile() {
        return requestsOperationsService.otherRequests(urlsProp.getProfileUrl(), CollectionEnum.PROFILE.d(), PROFILE_USER);
    }

    public boolean lifetime() {
        return requestsOperationsService.otherRequests(urlsProp.getLifetimeUrl(), CollectionEnum.A_LIFETIME.d(), null);
    }

    public boolean frequence() {
        return requestsOperationsService.otherRequests(urlsProp.getFrequenceUrl(), CollectionEnum.A_FREQUENCE.d(), FREQUENCE_CATEGORIES);
    }

}
