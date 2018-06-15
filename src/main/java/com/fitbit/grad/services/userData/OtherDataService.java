package com.fitbit.grad.services.userData;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    private final RequestsOperationsService requestsOperationsService;
    private final Environment env;

    @Autowired
    public OtherDataService(RequestsOperationsService requestsOperationsService, Environment env) {
        this.requestsOperationsService = requestsOperationsService;
        this.env = env;
    }

    public boolean profile() {
        return requestsOperationsService.otherRequests(env.getProperty("fitbitApiUrls.profileUrl"), CollectionEnum.PROFILE.d(), PROFILE_USER);
    }

    public boolean lifetime() {
        return requestsOperationsService.otherRequests(env.getProperty("fitbitApiUrls.lifetimeUrl"), CollectionEnum.A_LIFETIME.d(), null);
    }

    public boolean frequence() {
        return requestsOperationsService.otherRequests(env.getProperty("fitbitApiUrls.frequenceUrl"), CollectionEnum.A_FREQUENCE.d(), FREQUENCE_CATEGORIES);
    }

}
