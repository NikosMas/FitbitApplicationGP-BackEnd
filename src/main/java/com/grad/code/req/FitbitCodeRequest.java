package com.grad.code.req;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * set up the URI for authorization code and open it to the browser
 * 
 * @author nikos_mas
 *
 */

@Service
public class FitbitCodeRequest {

	static Logger log = LoggerFactory.getLogger("Fitbit application");

	public void codeRequest() throws IOException, InterruptedException{
		
		log.info("-- THE FOLLOWING LOGS DESCRIBE THE APPROACH TO FITBIT API FOR AUTHORIZATION CODE --");
		
		final String URI = "https://www.fitbit.com/oauth2/authorize?"
				+ "redirect_uri=http://localhost:8080"
				+ "&response_type=code"
				+ "&client_id=227MLG"
				+ "&scope=activity%20nutrition%20heartrate%20profile%20settings%20sleep%20social%20weight"
				+ "&expires_in=2592000"
				+ "&prompt=login";
			
		log.info("-> WE JUST CREATE THE URI TO BE SENT TO THE FITBIT API WITH THE REQUIRED HEADERS AND SEND IT TO A BROWSER <-");
		
		Runtime open_browser = Runtime.getRuntime();
		open_browser.exec( "rundll32 url.dll,FileProtocolHandler " + URI);
		   
		log.info("-> WE PUT A SLEEP OF 30 SECONDS WAITING THE USER TO COMPLETE THE VERIFICATION REQUIRED AND THE AUTHORIZATION CODE SUCCESSFULLY BE SAVED TO REDIS <-");
		
		Thread.sleep(30000);
	}
}
