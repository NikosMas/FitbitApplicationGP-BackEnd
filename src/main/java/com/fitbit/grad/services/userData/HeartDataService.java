package com.fitbit.grad.services.userData;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.domain.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Service about requesting to fitbit api for heart-rate data
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class HeartDataService {

	// filtered field from response
	private static final String HEART = "activities-heart";

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	private SaveOperationsService saveOperationsService;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private RestTemplate restTemplateGet;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private FitbitApiUrlProperties urlsProp;

	/**
	 * @param dates
	 * @return
	 */
	public boolean filterHeartRateValues(List<Map<String, String>> dates) {

		String p = calendarService.months(dates).stream().filter(month -> getFilterHeartRate(month) == false)
				.findFirst().orElse(null);

		return (p == null) ? true : false;
	}

	/**
	 * @param month
	 * @return
	 */
	private boolean getFilterHeartRate(String month) {
		try {

			JSONArray responseDataArray = heartCallResponse(month);
			if (responseDataArray != null) {
				for (int rda = 0; rda < responseDataArray.length(); rda++) {
					JSONArray heartRateZonesArray = getValues(responseDataArray, rda);
					for (int hrza = 0; hrza < heartRateZonesArray.length(); hrza++) {
						insertHeartRateValues(responseDataArray, rda, heartRateZonesArray, hrza);
					}
				}
				return true;
			}
			return false;
		} catch (IOException | JSONException e) {
			LOG.error("Something went wrong: ", e);
			return false;
		}
	}

	/**
	 * @param responseDataArray
	 * @param rda
	 * @param heartRateZonesArray
	 * @param hrza
	 * @throws JSONException
	 */
	private void insertHeartRateValues(JSONArray responseDataArray, int rda, JSONArray heartRateZonesArray, int hrza)
			throws JSONException {
		DBObject heartRateZonesValue = (DBObject) JSON.parse(heartRateZonesArray.getJSONObject(hrza).toString());
		heartRateZonesValue.put("date", responseDataArray.getJSONObject(rda).getString("dateTime"));
		heartRateZonesValue.put("month", responseDataArray.getJSONObject(rda).getString("dateTime").substring(0, 7));
		mongoTemplate.insert(heartRateZonesValue, CollectionEnum.FILTERD_A_HEART.d());
	}

	private JSONArray getValues(JSONArray responseDataArray, int i) throws JSONException {
		return responseDataArray.getJSONObject(i).getJSONObject("value").getJSONArray("heartRateZones");
	}

	/**
	 * @param month
	 * @return
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws JSONException 
	 */
	private JSONArray heartCallResponse(String month) throws JsonProcessingException, IOException, JSONException {
		ResponseEntity<String> heart = restTemplateGet.exchange(urlsProp.getHeartUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(false), String.class);

		if (heart.getStatusCodeValue() == 401) {
			ResponseEntity<String> heartWithRefreshToken = restTemplateGet.exchange(urlsProp.getHeartUrl() + month, HttpMethod.GET,saveOperationsService.getEntity(true), String.class);
			saveOperationsService.dataTypeInsert(heartWithRefreshToken, CollectionEnum.A_HEART.d(), HEART);
			return new JSONObject(heartWithRefreshToken.getBody()).getJSONArray(HEART);
		} else if (heart.getStatusCodeValue() == 200) {
			saveOperationsService.dataTypeInsert(heart, CollectionEnum.A_HEART.d(), HEART);
			return new JSONObject(heart.getBody()).getJSONArray(HEART);
		} else {
			return null;

		}
	}
}
