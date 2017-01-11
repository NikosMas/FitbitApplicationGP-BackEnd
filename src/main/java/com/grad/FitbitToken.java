package com.grad;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.JsonNode;
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

	public String token(){
	
	    log.info("-- THE FOLLOWING LOGS DESCRIBE THE ACCESS_TOKEN RETRIEVING PROCESS --");
		
		log.info("-> CONNECT TO REDIS DB AND SELECT THE AUTHORIZATION CODE <-");
		
	    MultiValueMap<String, String> paramsToken = new LinkedMultiValueMap<String, String>();
	    paramsToken.add("clientId", "227MLG");
	    paramsToken.add("grant_type", "authorization_code");
	    paramsToken.add("redirect_uri", "http://localhost:8080");
	    paramsToken.add("code", redisTemplate.opsForValue().get("AuthorizationCode"));
	    
	    log.info("-> SET THE REQUIRED PARAMETERS(CREDENTIALS AND THE AUTHORIZATION CODE) FOR THE POST REQUEST TO FITBIT API <-");
	    
	    HttpHeaders headersToken = new HttpHeaders();
	    headersToken.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    headersToken.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headersToken.set("Authorization", "Basic MjI3TUxHOjlkNTUwNjIxYzBlZTQ0ODkzNGM4MDQxYzQ1NjA3MTcx");
	    headersToken.set("Accept", "application/json");
	    
	    log.info("-> SET THE REQUIRED HEADER(AUTHORIZATION, SENDING AND RETURNING TYPE OF DATA) FOR THE POST REQUEST TO THE FITBIT API <-");
	    
	    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(paramsToken, headersToken);
	    ResponseEntity<String> token = restTemplateToken.exchange(uriToken, HttpMethod.POST, entity, String.class);
	    
	    log.info("-> SENDING THE POST REQUEST(ALONG WITH THE PARAMS AND HEADERS) TO GET THE ACCESS_TOKEN FROM THE FITBIT API <-");
	    
		try {
			JsonNode jsonResponseToken = mapperToken.readTree(token.getBody()).path("access_token");
		    String accessToken = jsonResponseToken.toString().substring(1, jsonResponseToken.toString().length()-1);
		    
		    log.info("-> THE ACCESS_TOKEN IS RETRIEVED AND READY FOR USE <-");
		    log.info("-> THE DATA RETRIEVING AND SAVING START FROM NOW <-");
		    
		    return accessToken;
		    
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
