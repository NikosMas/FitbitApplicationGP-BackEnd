package com.grad.auth.services;

import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grad.config.AuthorizationProperties;

/**
 * @author nikos_mas
 */

@Service
public class AuthCodeRequestService {

	@Autowired
	private AuthorizationProperties properties;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
	private static String OS = System.getProperty("os.name");

	public void codeRequest() throws IOException, InterruptedException, URISyntaxException {

		if (OS.equalsIgnoreCase("windows")) {
			
			Runtime openBrowser = Runtime.getRuntime();
			openBrowser.exec("rundll32 url.dll,FileProtocolHandler " + properties.getAuthCodeUri());
		
		} else if (OS.equalsIgnoreCase("linux")) {
			
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec("xdg-open " + properties.getAuthCodeUri());
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
			
		}
	}
}
