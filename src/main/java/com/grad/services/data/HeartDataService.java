package com.grad.services.data;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.grad.domain.CollectionEnum;
import com.grad.services.calendar.CalendarService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * @author nikos_mas
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HeartDataService {

	// URI for heart data. body part
	private static final String URI_HEART = "https://api.fitbit.com/1/user/-/activities/heart/date/";

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
		mongoTemplate.insert(heartRateZonesValue, CollectionEnum.HEART_RATE.description());
	}

	private JSONArray getValues(JSONArray responseDataArray, int i) throws JSONException {
		return responseDataArray.getJSONObject(i).getJSONObject("value").getJSONArray("heartRateZones");
	}

	/**
	 * @param month
	 * @return
	 */
	private JSONArray heartCallResponse(String month) {
		try {
			ResponseEntity<String> heart = restTemplateGet.exchange(URI_HEART + month, HttpMethod.GET,
					saveOperationsService.getEntity(false), String.class);

			if (heart.getStatusCodeValue() == 401) {
				ResponseEntity<String> heartWithRefreshToken = restTemplateGet.exchange(URI_HEART + month,
						HttpMethod.GET, saveOperationsService.getEntity(true), String.class);
				saveOperationsService.dataTypeInsert(heartWithRefreshToken,
						CollectionEnum.ACTIVITIES_HEART.description(), HEART);
				return new JSONObject(heartWithRefreshToken.getBody()).getJSONArray(HEART);
			} else if (heart.getStatusCodeValue() == 200) {
				saveOperationsService.dataTypeInsert(heart, CollectionEnum.ACTIVITIES_HEART.description(), HEART);
				return new JSONObject(heart.getBody()).getJSONArray(HEART);
			} else {
				return null;
			}
		} catch (JSONException | IOException e) {
			LOG.error(e.toString());
			return null;

		}
	}
}
