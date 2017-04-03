package com.grad;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.mail.MessagingException;
import javax.script.ScriptException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

import com.grad.auth.FitbitCodeRequest;
import com.grad.config.AuthorizationProperties;
import com.grad.config.MailInfoProperties;
import com.grad.config.MongoProperties;
import com.grad.heart.services.FitbitHeartCheckPeakService;

/**
 * @author nikos_mas
 */

@SpringBootApplication
@EnableConfigurationProperties({ MongoProperties.class, AuthorizationProperties.class, MailInfoProperties.class })
public class FitbitApplication {

	static Logger log = LoggerFactory.getLogger("Fitbit application");

	public static void main(String[] args) throws IOException, URISyntaxException, ScriptException, BeansException,
			JSONException, MessagingException, InterruptedException {
		ConfigurableApplicationContext appContext = SpringApplication.run(FitbitApplication.class, args);

		appContext.getBean(FitbitCodeRequest.class).codeRequest();

		log.info("-> THE PROCEDURE OF ACCESS_TOKEN RETRIEVER, DATA RETRIEVER AND SAVE COULD START BY NOW <-");

		appContext.getBean(FitbitCalls.class).dataCalls();

		log.info("-> DATA ARE NOW SAVED INTO THE MONGO DATABASE <-");

		log.info(
				"-> CHECKING THE HEART RATE DATA FOR DATES WITH MUCH TIME ON 'PEAK' ZONE AND SENDING MAIL TO THE USER <-");

		appContext.getBean(FitbitHeartCheckPeakService.class).heartRateSelect();
	}
}
