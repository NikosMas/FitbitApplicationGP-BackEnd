package com.fitbit.grad.services.operations;

import com.fitbit.grad.models.CollectionEnum;
import com.fitbit.grad.services.authRequests.AccessTokenRequestService;
import com.fitbit.grad.services.authRequests.RefreshTokenRequestService;
import javaslang.control.Option;
import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static javaslang.control.Option.*;

/**
 * Service containing tool methods for the requests to fitbit api
 *
 * @author nikos_mas, alex_kak
 */


@Service
public class RequestsOperationsService {

    @Autowired
    private AccessTokenRequestService tokenService;

    @Autowired
    private RefreshTokenRequestService refreshTokenService;

    @Autowired
    private SaveOperationsService saveOperationsService;

    @Autowired
    private RestTemplate restTemplate;

    private static String accessToken;
    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    /**
     * @param unauthorized that declares if refresh token should be used
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    public HttpEntity<String> getEntity(boolean unauthorized) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        if (unauthorized) {
            headers.set("Authorization", "Bearer " + refreshTokenService.refreshToken());
        } else {
            headers.set("Authorization", "Bearer " + getAccessToken());
        }

        return new HttpEntity<>(headers);
    }

    /**
     * if accessToken is null the service for new is called
     *
     * @return
     * @throws JsonProcessingException
     * @throws IOException
     */
    protected String getAccessToken() throws IOException {

        if (null == accessToken)
            accessToken = tokenService.token();

        return accessToken;
    }

    /**
     * sends a call to the given url & if response is unauthorized -> refresh token
     * and resends
     *
     * @param url
     * @param month
     * @param collection
     * @param fcollection
     * @return
     */
    public boolean requests(String url, String month, String collection, String fcollection) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url + month, HttpMethod.GET, getEntity(false), String.class);

            if (401 == response.getStatusCodeValue()) {
                ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url + month, HttpMethod.GET, getEntity(true), String.class);
                saveOperationsService.dataTypeInsert(responseWithRefreshToken, collection, fcollection);
                return true;
            } else if (200 == response.getStatusCodeValue()) {
                saveOperationsService.dataTypeInsert(response, collection, fcollection);
                return true;
            }
            return false;
        } catch (IOException | RestClientException e) {
            LOG.error("Something went wrong: ", e);
            return false;
        }
    }

    /**
     * @param url
     * @param collection
     * @throws IOException
     */
    public boolean dailyRequests(String url, String collection) throws IOException {
        ResponseEntity<String> response = restTemplate.exchange(url + "today/1d/time/00:00/" + LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET, getEntity(false), String.class);

        if (401 == response.getStatusCodeValue()) {
            ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url + "today/1d/time/00:00/" +
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET, getEntity(true), String.class);
            saveOperationsService.dailySave(responseWithRefreshToken.getBody(), collection);
            return true;
        } else if (200 == response.getStatusCodeValue()) {
            saveOperationsService.dailySave(response.getBody(), collection);
            return true;
        }
        return false;
    }

    /**
     * sends a call to the given url & if response is unauthorized -> refresh token
     * and resends
     *
     * @param month
     * @param heartUrl
     * @param filter
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public Option<JSONArray> heartRequests(String month, String heartUrl, String filter) throws IOException, JSONException {
        ResponseEntity<String> heart = restTemplate.exchange(heartUrl + month, HttpMethod.GET, getEntity(false), String.class);

        if (401 == heart.getStatusCodeValue()) {
            ResponseEntity<String> heartWithRefreshToken = restTemplate.exchange(heartUrl + month, HttpMethod.GET, getEntity(true), String.class);
            saveOperationsService.dataTypeInsert(heartWithRefreshToken, CollectionEnum.A_HEART.d(), filter);
            return of(new JSONObject(heartWithRefreshToken.getBody()).getJSONArray(filter));
        } else if (200 == heart.getStatusCodeValue()) {
            saveOperationsService.dataTypeInsert(heart, CollectionEnum.A_HEART.d(), filter);
            return of(new JSONObject(heart.getBody()).getJSONArray(filter));
        }
        return none();
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
    public boolean otherRequests(String url, String collection, String fcollection) {
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, getEntity(false), String.class);

            if (401 == response.getStatusCodeValue()) {
                ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url, HttpMethod.GET, getEntity(true), String.class);
                saveOperationsService.dataTypeInsert(responseWithRefreshToken, collection, fcollection);
                return true;
            } else if (200 == response.getStatusCodeValue()) {
                saveOperationsService.dataTypeInsert(response, collection, fcollection);
                return true;
            }
            return false;
        } catch (IOException | RestClientException e) {
            LOG.error("Something went wrong: ", e);
            return false;
        }
    }
}
