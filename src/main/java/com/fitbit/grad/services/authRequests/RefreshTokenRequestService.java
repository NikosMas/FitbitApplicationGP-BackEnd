package com.fitbit.grad.services.authRequests;

import com.fitbit.grad.config.AuthorizationProperties;
import com.fitbit.grad.config.RefreshTokenProperties;
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
 * Service about receiving refresh token from fitbit api
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class RefreshTokenRequestService {

    private final RefreshTokenProperties refreshProp;
    private final AuthorizationProperties authProp;
    private final ObjectMapper mapper;
    private final RestTemplate restTemplate;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RefreshTokenRequestService(RefreshTokenProperties refreshProp, AuthorizationProperties authProp, ObjectMapper mapper, RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.refreshProp = refreshProp;
        this.authProp = authProp;
        this.mapper = mapper;
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    public String refreshToken() throws IOException {

        String headerAuth = Base64.getEncoder().encodeToString(
                (redisTemplate.opsForValue().get("Client-id") + ":" + redisTemplate.opsForValue().get("Client-secret"))
                        .getBytes("utf-8"));

        // request parameters
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("grant_type", refreshProp.getGrantType());
        parameters.add("refresh_token", redisTemplate.opsForValue().get("RefreshToken"));

        // request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Basic " + headerAuth);
        headers.set("Accept", refreshProp.getHeaderAccept());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.exchange(authProp.getTokenUrl(), HttpMethod.POST, entity, String.class);

        JsonNode jsonResponse = mapper.readTree(response.getBody()).path("access_token");

        return jsonResponse.toString().substring(1, jsonResponse.toString().length() - 1);
    }
}
