package com.fitbit.grad;

import java.io.IOException;

import com.fitbit.grad.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author nikos_mas, alex_kak
 */

@EnableConfigurationProperties({ AuthorizationProperties.class, MailInfoProperties.class, RefreshTokenProperties.class,
		FitbitApiUrlProperties.class, DownloadingProperties.class})
@SpringBootApplication
public class FitbitApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FitbitApplication.class, args);

		String OS = System.getProperty("os.name");
		if (OS.equalsIgnoreCase("linux")) {
			Runtime.getRuntime().exec("xdg-open http://localhost:8080/fitbitApp/dashboard");
		} else {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://localhost:8080/fitbitApp/dashboard");
		}
	}
}
