package com.grad.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.mail.MessagingException;

import org.codehaus.jackson.JsonProcessingException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grad.auth.services.AuthCodeRequestService;
import com.grad.collections.CreateCollectionsService;
import com.grad.data.services.FitbitDataStoreService;
import com.grad.heart.services.FitbitHeartCheckPeakService;

/**
 * rest-controller waits the Fitbit to send the authorisation code required for
 * Token receiver
 * 
 * @author nikos_mas
 *
 */

@RestController
public class AuthorizationCodeController {

	private FitbitHeartCheckPeakService heartService;
	private FitbitDataStoreService callsService;
	private CreateCollectionsService collectionsService;
	private AuthCodeRequestService codeService;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@Autowired
	public AuthorizationCodeController(FitbitHeartCheckPeakService heartService, FitbitDataStoreService callsService,
			CreateCollectionsService collectionsService, AuthCodeRequestService codeService) {

		this.callsService = callsService;
		this.codeService = codeService;
		this.collectionsService = collectionsService;
		this.heartService = heartService;
	}

	@RequestMapping("/fitbitApplication")
	public void authorization() throws IOException, InterruptedException, URISyntaxException {

		collectionsService.collectionsCreate();
		codeService.codeRequest();
	}

	@RequestMapping("/")
	public void authorization(@RequestParam(value = "code") String code)
			throws JsonProcessingException, JSONException, IOException, MessagingException {

		redisTemplate.opsForValue().set("AuthorizationCode", code);
		callsService.dataCalls();
		heartService.heartRateSelect();
	}

}
