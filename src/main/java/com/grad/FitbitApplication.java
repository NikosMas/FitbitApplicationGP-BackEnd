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

import com.grad.auth.services.AuthCodeRequestService;
import com.grad.collections.CreateCollectionsService;
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

		appContext.getBean(CreateCollectionsService.class).collectionsCreate();
		appContext.getBean(AuthCodeRequestService.class).codeRequest();
		appContext.getBean(FitbitCalls.class).dataCalls();
		appContext.getBean(FitbitHeartCheckPeakService.class).heartRateSelect();
	}
}
