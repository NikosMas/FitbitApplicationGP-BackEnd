package com.fitbit.grad.services.userData;

import com.fitbit.grad.services.authRequests.AccessTokenRequestService;
import com.fitbit.grad.services.authRequests.RefreshTokenRequestService;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

@Service
public class RequestsOperationsService {

    @Autowired
    private AccessTokenRequestService tokenService;

    @Autowired
    private RefreshTokenRequestService refreshTokenService;

    @Autowired
    private SaveOperationsService saveOperationsService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

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

        if (accessToken == null)
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
            ResponseEntity<String> response = restTemplate.exchange(url + month, HttpMethod.GET,	getEntity(false), String.class);

            if (response.getStatusCodeValue() == 401) {
                ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url + month, HttpMethod.GET,	getEntity(true), String.class);
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

    /**
     * @param url
     * @param collection
     * @throws JsonProcessingException
     * @throws IOException
     */
    public void dailyRequests(String url, String collection) throws IOException {
        ResponseEntity<String> response = restTemplate.exchange(url + "today/1d/time/00:00/" + LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET, getEntity(false), String.class);

        if (response.getStatusCodeValue() == 401) {
            ResponseEntity<String> responseWithRefreshToken = restTemplate.exchange(url + "today/1d/time/00:00/" +
                    LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + ".json", HttpMethod.GET,getEntity(true), String.class);

            JsonNode responseDataBody = mapper.readTree(responseWithRefreshToken.getBody());
            DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());
            mongoTemplate.insert(dataToInsert, collection);

        } else if (response.getStatusCodeValue() == 200) {
            JsonNode responseDataBody = mapper.readTree(response.getBody());
            DBObject dataToInsert = (DBObject) JSON.parse(responseDataBody.toString());
            mongoTemplate.insert(dataToInsert, collection);
        }
    }
}
