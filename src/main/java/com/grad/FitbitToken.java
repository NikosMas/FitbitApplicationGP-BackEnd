package com.grad;

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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * create a rest template to execute the requests to receive the Token required for the data calls
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitToken {

	static Logger log = LoggerFactory.getLogger("Fitbit application");
	@Autowired
	private ObjectMapper mapperToken;
	
	@Autowired
	private RestTemplate restTemplateToken;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private static final String uriToken = "https://api.fitbit.com/oauth2/token";

	public String token() throws JsonProcessingException, IOException{
	
	    log.info("-- THE FOLLOWING LOGS DESCRIBE THE ACCESS_TOKEN RETRIEVING PROCESS --");
		
		log.info("-> CONNECT TO REDIS DB AND SELECT THE AUTHORIZATION CODE <-");
		
	    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
	    parameters.add("clientId", "227MLG");
	    parameters.add("grant_type", "authorization_code");
	    parameters.add("redirect_uri", "http://localhost:8080");
	    parameters.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));
	    
	    log.info("-> SET THE REQUIRED PARAMETERS(CREDENTIALS AND THE AUTHORIZATION CODE) FOR THE POST REQUEST TO FITBIT API <-");
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.set("Authorization", "Basic MjI3TUxHOjlkNTUwNjIxYzBlZTQ0ODkzNGM4MDQxYzQ1NjA3MTcx");
	    headers.set("Accept", "application/json");
	    
	    log.info("-> SET THE REQUIRED HEADER(AUTHORIZATION, SENDING AND RETURNING TYPE OF DATA) FOR THE POST REQUEST TO THE FITBIT API <-");
	    
	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
	    ResponseEntity<String> response = restTemplateToken.exchange(uriToken, HttpMethod.POST, entity, String.class);
	    
	    log.info("-> SENDING THE POST REQUEST(ALONG WITH THE PARAMS AND HEADERS) TO GET THE ACCESS_TOKEN FROM THE FITBIT API <-");
	    
			JsonNode jsonResponse = mapperToken.readTree(response.getBody()).path("access_token");
		    String accessToken = jsonResponse.toString().substring(1, jsonResponse.toString().length()-1);
		   
		    JsonNode jsonResponseRefreshToken = mapperToken.readTree(response.getBody()).path("refresh_token");
		    String refreshToken = jsonResponseRefreshToken.toString().substring(1, jsonResponseRefreshToken.toString().length()-1);
		    
		    redisTemplate.opsForValue().set("RefreshToken", refreshToken);
		    
		    log.info("-> THE ACCESS_TOKEN IS RETRIEVED AND READY FOR USE <-");
		    log.info("-> THE DATA RETRIEVING AND SAVING START FROM NOW <-");
		    
		    return accessToken;
	}
}
