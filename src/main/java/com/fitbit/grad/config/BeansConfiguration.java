package com.fitbit.grad.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * beans configuration associated with required beans
 * 
 * @author nikos_mas, alex_kak
 */

@Configuration
public class BeansConfiguration {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
