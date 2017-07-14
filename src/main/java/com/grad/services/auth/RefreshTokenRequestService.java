package com.grad.services.auth;

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

import com.grad.config.AuthorizationProperties;
import com.grad.config.RefreshTokenProperties;

/**
 * Service about receiving refresh token from fitbit api
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class RefreshTokenRequestService {

	@Autowired
	private RefreshTokenProperties refreshProp;
	
	@Autowired
	private AuthorizationProperties authProp;

	@Autowired
	private ObjectMapper mapperToken;

	@Autowired
	private RestTemplate restTemplateToken;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public String refreshToken() throws JsonProcessingException, IOException {

		String headerAuth = Base64.getEncoder().encodeToString(
				(redisTemplate.opsForValue().get("Client-id") + ":" + redisTemplate.opsForValue().get("Client-secret"))
						.getBytes("utf-8"));
		
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("grant_type", refreshProp.getGrantType());
		parameters.add("refresh_token", redisTemplate.opsForValue().get("RefreshToken"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", "Basic " + headerAuth);
		headers.set("Accept", refreshProp.getHeaderAccept());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,
				headers);
		ResponseEntity<String> response = restTemplateToken.exchange(authProp.getTokenUrl(), HttpMethod.POST, entity, String.class);

		JsonNode jsonResponse = mapperToken.readTree(response.getBody()).path("access_token");
		String accessToken = jsonResponse.toString().substring(1, jsonResponse.toString().length() - 1);

		return accessToken;
	}
}
