package com.grad.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nikos_mas
 */

@RestController
public class AuthorizationCodeController {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

	@RequestMapping("/")
	public void authorization(@RequestParam(value = "code") String code){

		redisTemplate.opsForValue().set("AuthorizationCode", code);
		LOG.info("Authorization code saved into Redis database and it's ready for use");

	}

}
