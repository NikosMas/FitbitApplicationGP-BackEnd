package com.grad.auth.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.grad.config.AuthorizationProperties;

/**
 * @author nikos_mas
 */

@Service
public class AccessTokenRequestService {

	private static final Logger LOG = LoggerFactory.getLogger("Fitbit application");
	private static final String uriToken = "https://api.fitbit.com/oauth2/token";
	private static String accessToken;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private AuthorizationProperties properties;

	@Autowired
	private RestTemplate restTemplateToken;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public String token() throws JsonProcessingException, IOException {

		String headerAuth = Base64.getEncoder().encodeToString(
				(redisTemplate.opsForValue().get("Client-id") + ":" + redisTemplate.opsForValue().get("Client-secret"))
						.getBytes("utf-8"));

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("clientId", redisTemplate.opsForValue().get("Client-id"));
		parameters.add("grant_type", properties.getGrantType());
		parameters.add("redirect_uri", properties.getRedirectUri());
		parameters.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Basic " + headerAuth);
		headers.set("Accept", properties.getHeaderAccept());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,
				headers);
		ResponseEntity<String> response = restTemplateToken.exchange(uriToken, HttpMethod.POST, entity, String.class);

		JsonNode jsonResponse = mapper.readTree(response.getBody()).path("access_token");
		accessToken = jsonResponse.toString().substring(1, jsonResponse.toString().length() - 1);

		JsonNode jsonResponseRefreshToken = mapper.readTree(response.getBody()).path("refresh_token");
		String refreshToken = jsonResponseRefreshToken.toString().substring(1,
				jsonResponseRefreshToken.toString().length() - 1);

		redisTemplate.opsForValue().set("RefreshToken", refreshToken);

		return accessToken;
	}
}
