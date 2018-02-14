package com.fitbit.grad.services.authRequests;

import com.fitbit.grad.config.AuthorizationProperties;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

/**
 * Service about receiving access token from fitbit api
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class AccessTokenRequestService {

    private static String accessToken;

    private final ObjectMapper mapper;
    private final AuthorizationProperties authProp;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AccessTokenRequestService(ObjectMapper mapper, AuthorizationProperties authProp, RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.mapper = mapper;
        this.authProp = authProp;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public String token() throws IOException {

        String headerAuth = Base64.getEncoder().encodeToString(
                (redisTemplate.opsForValue().get("Client-id") + ":" + redisTemplate.opsForValue().get("Client-secret"))
                        .getBytes("utf-8"));

        // request parameters
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("clientId", redisTemplate.opsForValue().get("Client-id"));
        parameters.add("grant_type", authProp.getGrantType());
        parameters.add("redirect_uri", authProp.getRedirectUri());
        parameters.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Basic " + headerAuth);
        headers.set("Accept", authProp.getHeaderAccept());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.exchange(authProp.getTokenUrl(), HttpMethod.POST, entity, String.class);

        JsonNode jsonResponse = mapper.readTree(response.getBody()).path("access_token");
        accessToken = jsonResponse.toString().substring(1, jsonResponse.toString().length() - 1);

        JsonNode jsonResponseRefreshToken = mapper.readTree(response.getBody()).path("refresh_token");
        String refreshToken = jsonResponseRefreshToken.toString().substring(1,
                jsonResponseRefreshToken.toString().length() - 1);

        redisTemplate.opsForValue().set("RefreshToken", refreshToken);

        return accessToken;
    }
}
