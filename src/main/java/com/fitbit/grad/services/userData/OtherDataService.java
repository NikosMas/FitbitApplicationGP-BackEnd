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
import com.fitbit.grad.domain.CollectionEnum;

/**
 * Service about requesting to fitbit api for profile, lifetime activities & frequent activities data
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class OtherDataService {

	private static final String PROFILE_USER = "user";
	private static final String FREQUENCE_CATEGORIES = "categories";

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private SaveOperationsService dataService;

	@Autowired
	private RestTemplate restTemplateGet;
	
	@Autowired
	private FitbitApiUrlProperties urlsProp;

	/**
	 * @return
	 */
	
	public boolean profile() {
		try {
			ResponseEntity<String> profile;
			profile = restTemplateGet.exchange(urlsProp.getProfileUrl(), HttpMethod.GET, dataService.getEntity(false), String.class);

			if (profile.getStatusCodeValue() == 401) {
				ResponseEntity<String> profileWithRefreshToken = restTemplateGet.exchange(urlsProp.getProfileUrl(), HttpMethod.GET,dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(profileWithRefreshToken, CollectionEnum.PROFILE.d(),PROFILE_USER);
				return true;
			} else if (profile.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(profile, CollectionEnum.PROFILE.d(), PROFILE_USER);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}

	/**
	 * @return
	 */
	
	public boolean lifetime() {
		try {
			ResponseEntity<String> lifetime;
			lifetime = restTemplateGet.exchange(urlsProp.getLifetimeUrl(), HttpMethod.GET, dataService.getEntity(false),	String.class);
			if (lifetime.getStatusCodeValue() == 401) {
				ResponseEntity<String> lifetimeWithRefreshToken = restTemplateGet.exchange(urlsProp.getLifetimeUrl(), HttpMethod.GET,dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(lifetimeWithRefreshToken,CollectionEnum.A_LIFETIME.d(), null);
				return true;
			} else if (lifetime.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(lifetime, CollectionEnum.A_LIFETIME.d(), null);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}

	/**
	 * @return
	 */
	
	public boolean frequence() {
		try {
			ResponseEntity<String> frequence;
			frequence = restTemplateGet.exchange(urlsProp.getFrequenceUrl(), HttpMethod.GET, dataService.getEntity(false),String.class);

			if (frequence.getStatusCodeValue() == 401) {
				ResponseEntity<String> frequenceWithRefreshToken = restTemplateGet.exchange(urlsProp.getFrequenceUrl(),HttpMethod.GET, dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(frequenceWithRefreshToken,CollectionEnum.A_FREQUENCE.d(), FREQUENCE_CATEGORIES);
				return true;
			} else if (frequence.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(frequence, CollectionEnum.A_FREQUENCE.d(),FREQUENCE_CATEGORIES);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}
}
