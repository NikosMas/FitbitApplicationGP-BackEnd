package com.fitbit.grad;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author nikos_mas, alex_kak
 */

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

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
