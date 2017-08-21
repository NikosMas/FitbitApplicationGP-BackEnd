package com.fitbit.grad.services.authRequests;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
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

import com.fitbit.grad.config.AuthorizationProperties;

/**
 * Service about receiving access token from fitbit api
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class AccessTokenRequestService {

	private static String accessToken;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private AuthorizationProperties authProp;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String token() throws IOException {

		String headerAuth = Base64.getEncoder().encodeToString(
				(redisTemplate.opsForValue().get("Client-id") + ":" + redisTemplate.opsForValue().get("Client-secret"))
						.getBytes("utf-8"));

		// request parameters
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("clientId", redisTemplate.opsForValue().get("Client-id"));
		parameters.add("grant_type", authProp.getGrantType());
		parameters.add("redirect_uri", authProp.getRedirectUri());
		parameters.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));

		// request headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Basic " + headerAuth);
		headers.set("Accept", authProp.getHeaderAccept());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,headers);
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
