package com.grad;

import java.io.IOException;
import java.util.Arrays;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

public class FitbitToken {

	static Logger LOGGER = LoggerFactory.getLogger("FITBIT_GRAD_APP");
	
	public static String token(){
	
	final String uriToken = "https://api.fitbit.com/oauth2/token";
    
    RestTemplate restTemplateToken = new RestTemplate();
    LOGGER.info("- THE FOLLOWING LOGS DESCRIBE THE ACCESS_TOKEN RETRIEVING PROCESS -");
    @SuppressWarnings("resource")
	Jedis jedis = new Jedis("localhost");
	String value = jedis.get("AuthorizationCode");
	LOGGER.info("-> CONNECT TO REDIS DB AND SELECT THE AUTHORIZATION CODE");
	
    MultiValueMap<String, String> paramsToken = new LinkedMultiValueMap<String, String>();
    paramsToken.add("clientId", "227MLG");
    paramsToken.add("grant_type", "authorization_code");
    paramsToken.add("redirect_uri", "http://localhost:8080");
    paramsToken.add("code", value);
    LOGGER.info("-> SET THE REQUIRED PARAMETERS(CREDENTIALS AND THE AUTHORIZATION CODE) FOR THE POST REQUEST TO FITBIT API");
    
    HttpHeaders headersToken = new HttpHeaders();
    headersToken.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headersToken.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headersToken.set("Authorization", "Basic MjI3TUxHOjlkNTUwNjIxYzBlZTQ0ODkzNGM4MDQxYzQ1NjA3MTcx");
    headersToken.set("Accept", "application/json");
    LOGGER.info("-> SET THE REQUIRED HEADER(AUTHORIZATION, SENDING AND RETURNING TYPE OF DATA) FOR THE POST REQUEST TO THE FITBIT API");
    
    HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(paramsToken, headersToken);
    
    ResponseEntity<String> token = restTemplateToken.exchange(uriToken, HttpMethod.POST, entity, String.class);
    LOGGER.info("-> SENDING THE POST REQUEST(ALONG WITH THE PARAMS AND HEADERS) TO GET THE ACCESS_TOKEN FROM THE FITBIT API");
    
	ObjectMapper mapperToken = new ObjectMapper();		
	JsonNode rootToken;
	try {
		rootToken = mapperToken.readTree(token.getBody());
		JsonNode jsonResponseToken = rootToken.path("access_token");
	    String accessToken = jsonResponseToken.toString().substring(1, jsonResponseToken.toString().length()-1);
	    LOGGER.info("-> THE ACCESS_TOKEN IS RETRIEVED AND READY FOR USE");
	    return accessToken;
	    
	} catch (JsonProcessingException e) {e.printStackTrace();
	} catch (IOException e) {e.printStackTrace();}
	return null;		
	}
}
