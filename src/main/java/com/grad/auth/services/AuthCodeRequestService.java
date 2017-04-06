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
	
	static Logger log = LoggerFactory.getLogger("Fitbit application");

	public void codeRequest() throws IOException, InterruptedException, URISyntaxException {

		Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("xdg-open " + properties.getAuthCodeUri());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    
		
//		Runtime openBrowser = Runtime.getRuntime();
//		openBrowser.exec("rundll32 url.dll,FileProtocolHandler " + properties.getAuthCodeUri());

		// TODO: change this with something better

		//Thread.sleep(30000);
	}
}
