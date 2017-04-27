package com.grad.auth.services;

import java.io.IOException;
import java.util.Arrays;
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
import org.springframework.http.HttpStatus;
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

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	private static final String uriToken = "https://api.fitbit.com/oauth2/token";

	@Autowired
	private AuthorizationProperties properties;

	@Autowired
	private RestTemplate restTemplateToken;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public ResponseEntity<String> token() throws JsonProcessingException, IOException {

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.add("clientId", properties.getClientid());
		parameters.add("grant_type", properties.getGrantType());
		parameters.add("redirect_uri", properties.getRedirectUri());
		parameters.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", properties.getHeaderAuth());
		headers.set("Accept", properties.getHeaderAccept());

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters,
				headers);

		return restTemplateToken.exchange(uriToken, HttpMethod.POST, entity, String.class);
	}
}
