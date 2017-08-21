package com.fitbit.grad.services.userData;

import com.fitbit.grad.config.FitbitApiUrlProperties;
import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.calendar.CalendarService;
import com.fitbit.grad.services.operations.RequestsOperationsService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.json.JSONArray;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	private RequestsOperationsService requestsOperationsService;

	@Autowired
	private CalendarService calendarService;

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
	 * Go inside the response and get all the heart rate zones with values and dates 
	 * 
	 * @param month
	 * @return
	 */
	private boolean getFilterHeartRate(String month) {
		try {
			JSONArray responseDataArray = requestsOperationsService.heartRequests(month, urlsProp.getHeartUrl(), HEART).getOrElse(() -> null);
			if (responseDataArray == null) return false;
			for (int rda = 0; rda < responseDataArray.length(); rda++) {
                JSONArray heartRateZonesArray = responseDataArray.getJSONObject(rda).getJSONObject("value").getJSONArray("heartRateZones");
                for (int hrza = 0; hrza < heartRateZonesArray.length(); hrza++) {
                    insertHeartRateValues(responseDataArray, rda, heartRateZonesArray, hrza);
                }
            }
			return true;
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

}
