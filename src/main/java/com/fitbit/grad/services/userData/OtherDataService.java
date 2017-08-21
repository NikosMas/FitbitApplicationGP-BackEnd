package com.fitbit.grad.services.userData;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;

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

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private SaveOperationsService saveOperationsService;

	@Autowired
	private RequestsOperationsService requestsOperationsService;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FitbitApiUrlProperties urlsProp;

	public boolean profile() {
		 
		return requests(urlsProp.getProfileUrl(), CollectionEnum.PROFILE.d(), PROFILE_USER);
	}
	
	public boolean lifetime() {
		
		return requests(urlsProp.getLifetimeUrl(), CollectionEnum.A_LIFETIME.d(), null);
	}
	
	public boolean frequence() {
		
		return requests(urlsProp.getFrequenceUrl(), CollectionEnum.A_FREQUENCE.d(), FREQUENCE_CATEGORIES);
	}
	
	/**
	 * sends a call to the given url & if response is unauthorized -> refresh token
	 * and resends
	 * 
	 * @param url
	 * @param collection
	 * @param fcollection
	 * @return
	 */
	private boolean requests(String url, String collection, String fcollection) {
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestsOperationsService.getEntity(false), String.class);

			if (response.getStatusCodeValue() == 401) {
				ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url, HttpMethod.GET, requestsOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(responseWithRefreshToken, collection, fcollection);
				return true;
			} else if (response.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(response, collection, fcollection);
				return true;
			} else {
				return false;
			}

		} catch (IOException | RestClientException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}
}
