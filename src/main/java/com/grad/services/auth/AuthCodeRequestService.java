package com.grad.services.auth;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.grad.config.AuthorizationProperties;

/**
 * Service about receiving authorization code from fitbit api
 * 
 * @author nikos_mas, alex_kak
 */

@Service
public class AuthCodeRequestService {

	@Autowired
	private AuthorizationProperties authProp;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	private static String OS = System.getProperty("os.name");

	public void codeRequest() {
		try {
			if (OS.equalsIgnoreCase("linux")) {

				Runtime.getRuntime().exec("xdg-open " + authProp.getAuthCodeUri1() + "client_id="
						+ redisTemplate.opsForValue().get("Client-id") + "&" + authProp.getAuthCodeUri2());
			} else {
				
				Runtime.getRuntime()
						.exec("rundll32 url.dll,FileProtocolHandler " + authProp.getAuthCodeUri1() + "client_id="
								+ redisTemplate.opsForValue().get("Client-id") + "&" + authProp.getAuthCodeUri2());
			}

		} catch (IOException e) {
			LOG.error("Something went wrong: ", e);
		}
	}
}
