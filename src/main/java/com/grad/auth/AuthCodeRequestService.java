package com.grad.auth;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.config.AuthorizationProperties;

/**
 * set up the URI for authorization code and open it to the browser
 * 
 * @author nikos_mas
 *
 */

@Service
public class AuthCodeRequestService {
	
	@Autowired
	private AuthorizationProperties properties;
	
	static Logger log = LoggerFactory.getLogger("Fitbit application");

	public void codeRequest() throws IOException, InterruptedException {

		Runtime openBrowser = Runtime.getRuntime();
		openBrowser.exec("rundll32 url.dll,FileProtocolHandler " + properties.getAuthCodeUri());

		// TODO: change this with something better

		Thread.sleep(30000);
	}
}
