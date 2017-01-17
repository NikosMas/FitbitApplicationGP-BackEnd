package com.grad;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.mail.MessagingException;
import javax.script.ScriptException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.client.RestTemplate;
import com.grad.code.req.FitbitCodeRequest;
import com.grad.heart.FitbitHeartTestPeak;
import com.mongodb.MongoClient;

/**
 * main class. includes the beans and callers for authorization-code class, data-calls class, heart-data  
 * 
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
	
		
	public static void main(String[] args) throws IOException, URISyntaxException, ScriptException, BeansException, JSONException, MessagingException, InterruptedException{
		ConfigurableApplicationContext appContext = SpringApplication.run(FitbitApplication.class, args);
		
		appContext.getBean(FitbitCodeRequest.class).codeRequest();
		
		log.info("-> THE PROCEDURE OF ACCESS_TOKEN RETRIEVER, DATA RETRIEVER AND SAVE COULD START BY NOW <-");    
		
		appContext.getBean(FitbitCalls.class).dataCalls();

		log.info("-> DATA ARE NOW SAVED INTO THE MONGO DATABASE <-");
		
		log.info("-> CHECKING THE HEART RATE DATA FOR DATES WITH MUCH TIME ON 'PEAK' ZONE AND SENDING MAIL TO THE USER <-");

		appContext.getBean(FitbitHeartTestPeak.class).heartRateSelect();
	}
}
