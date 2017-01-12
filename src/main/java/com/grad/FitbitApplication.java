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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.client.RestTemplate;
import com.mongodb.MongoClient;

/**
 * main class. includes the beans. sending the GET request through browser. calls the dataCalls 
 * 
 * @author nikos_mas
 *
 */

@SpringBootApplication
public class FitbitApplication {
	
	static Logger log = LoggerFactory.getLogger("Fitbit application");

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public MongoTemplate mongoTemplate() {
		final String DB_NAME = "fitbit";
		final String MONGO_HOST = "localhost";
		final int MONGO_PORT = 27017;
		
		MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		return new MongoTemplate(mongo, DB_NAME);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	 }

	public static void main(String[] args) throws IOException, URISyntaxException, ScriptException{
		ConfigurableApplicationContext appContext = SpringApplication.run(FitbitApplication.class, args);
		
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
		   
		log.info("-> WE PUT A DELAY OF 30 SECONDS WAITING THE USER TO COMPLETE THE VERIFICATION REQUIRED AND THE AUTHORIZATION CODE SUCCESSFULLY BE SAVED TO REDIS <-");
		
		try {TimeUnit.SECONDS.sleep(30);
		}catch (InterruptedException e)
		{e.printStackTrace();}
		
		log.info("-> THE PROCEDURE OF ACCESS_TOKEN RETRIEVER, DATA RETRIEVER AND SAVE COULD START BY NOW <-");    
		
		appContext.getBean(FitbitCalls.class).dataCalls();

		log.info("-> DATA ARE NOW SAVED INTO THE MONGO DATABASE <-");
	}
}
