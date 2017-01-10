package com.grad;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import javax.script.ScriptException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class FitbitApplication {
	
	static Logger LOGGER = LoggerFactory.getLogger("FITBIT_GRAD_APP");

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}


	public static void main(String[] args) throws IOException, URISyntaxException, ScriptException{
		ConfigurableApplicationContext appContext = SpringApplication.run(FitbitApplication.class, args);
		
		LOGGER.info("- THE FOLLOWING LOGS DESCRIBE THE APPROACH TO FITBIT API FOR AUTHORIZATION CODE -");
			String URI = "https://www.fitbit.com/oauth2/authorize?"
					+ "redirect_uri=http://localhost:8080"
					+ "&response_type=code"
					+ "&client_id=227MLG"
					+ "&scope=activity%20nutrition%20heartrate%20profile%20settings%20sleep%20social%20weight"
					+ "&expires_in=2592000"
					+ "&prompt=login";
			
		LOGGER.info("-> WE JUST CREATE THE URI TO BE SENT TO THE FITBIT API WITH THE REQUIRED HEADERS AND SEND IT TO A BROWSER");
		Runtime open_browser = Runtime.getRuntime();
		open_browser.exec( "rundll32 url.dll,FileProtocolHandler " + URI);
		   
		LOGGER.info("-> WE PUT A DELAY OF 30 SECONDS WAITING THE USER TO COMPLETE THE VERIFICATION REQUIRED AND THE AUTHORIZATION CODE SUCCESSFULLY BE SAVED TO REDIS");
		try {TimeUnit.SECONDS.sleep(30);
		}catch (InterruptedException e)
		{e.printStackTrace();}
		
		LOGGER.info("-> THE PROCEDURE OF ACCESS_TOKEN RETRIEVER, DATA RETRIEVER AND SAVE COULD START BY NOW");    
		
		appContext.getBean(FitbitCalls.class).dataCalls();

		LOGGER.info("-> FINALLY DATA ARE NOW SAVED WITH THE VIEW OF COLLECTIONS INTO THE MONGO DATABASE");
	}
}

		    
