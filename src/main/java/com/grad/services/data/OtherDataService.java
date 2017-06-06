package com.grad.services.data;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.grad.domain.CollectionEnum;

/**
 * @author nikos_mas
 */

@Service
public class OtherDataService {

	// URI for each data. body part
	private static final String URI_PROFILE = "https://api.fitbit.com/1/user/-/profile.json";
	private static final String URI_FREQUENCE = "https://api.fitbit.com/1/user/-/activities/frequence.json";
	private static final String URI_LIFETIME = "https://api.fitbit.com/1/user/-/activities.json";

	private static final String PROFILE_USER = "user";
	private static final String FREQUENCE_CATEGORIES = "categories";

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private SaveOperationsService dataService;

	@Autowired
	private RestTemplate restTemplateGet;

	/**
	 * @return
	 */
	
	public boolean profile() {
		try {
			ResponseEntity<String> profile;
			profile = restTemplateGet.exchange(URI_PROFILE, HttpMethod.GET, dataService.getEntity(false), String.class);

			if (profile.getStatusCodeValue() == 401) {
				ResponseEntity<String> profileWithRefreshToken = restTemplateGet.exchange(URI_PROFILE, HttpMethod.GET,dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(profileWithRefreshToken, CollectionEnum.PROFILE.desc(),PROFILE_USER);
				return true;
			} else if (profile.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(profile, CollectionEnum.PROFILE.desc(), PROFILE_USER);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error(e.toString());
			return false;
		}
	}

	/**
	 * @return
	 */
	
	public boolean lifetime() {
		try {
			ResponseEntity<String> lifetime;
			lifetime = restTemplateGet.exchange(URI_LIFETIME, HttpMethod.GET, dataService.getEntity(false),	String.class);
			if (lifetime.getStatusCodeValue() == 401) {
				ResponseEntity<String> lifetimeWithRefreshToken = restTemplateGet.exchange(URI_LIFETIME, HttpMethod.GET,dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(lifetimeWithRefreshToken,CollectionEnum.ACTIVITIES_LIFETIME.desc(), null);
				return true;
			} else if (lifetime.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(lifetime, CollectionEnum.ACTIVITIES_LIFETIME.desc(), null);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error(e.toString());
			return false;
		}
	}

	/**
	 * @return
	 */
	
	public boolean frequence() {
		try {
			ResponseEntity<String> frequence;
			frequence = restTemplateGet.exchange(URI_FREQUENCE, HttpMethod.GET, dataService.getEntity(false),String.class);

			if (frequence.getStatusCodeValue() == 401) {
				ResponseEntity<String> frequenceWithRefreshToken = restTemplateGet.exchange(URI_FREQUENCE,HttpMethod.GET, dataService.getEntity(true), String.class);
				dataService.dataTypeInsert(frequenceWithRefreshToken,CollectionEnum.ACTIVITIES_FREQUENCE.desc(), FREQUENCE_CATEGORIES);
				return true;
			} else if (frequence.getStatusCodeValue() == 200) {
				dataService.dataTypeInsert(frequence, CollectionEnum.ACTIVITIES_FREQUENCE.desc(),FREQUENCE_CATEGORIES);
				return true;
			}
			return false;

		} catch (RestClientException | IOException e) {
			LOG.error(e.toString());
			return false;
		}
	}
}
