package com.grad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.grad.config.AuthorizationProperties;
import com.grad.config.MailInfoProperties;
import com.grad.config.MongoProperties;
import com.grad.config.RefreshTokenProperties;

/**
 * @author nikos_mas
 */

@EnableConfigurationProperties({ MongoProperties.class, AuthorizationProperties.class, MailInfoProperties.class,
		RefreshTokenProperties.class })
@SpringBootApplication
public class FitbitApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitbitApplication.class, args);
	}
}
