package com.grad.config;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.client.RestTemplate;

import com.mongodb.MongoClient;

@Configuration
public class BeansConfiguration {

	@Autowired
	private MongoProperties properties;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(new MongoClient(properties.getHost(), properties.getPort()), properties.getDbname());
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}

}
