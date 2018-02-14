package com.fitbit.grad.controller;

import com.fitbit.grad.services.userData.DailyDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * rest controller on localhost waiting get request from fitbit api
 *
 * @author nikos_mas, alex_kak
 */

@RestController
public class AuthorizationCodeController {

    private final RedisTemplate<String, String> redisTemplate;
    private final DailyDataService dailyDataService;

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");

    @Autowired
    public AuthorizationCodeController(RedisTemplate<String, String> redisTemplate, DailyDataService dailyDataService) {
        this.redisTemplate = redisTemplate;
        this.dailyDataService = dailyDataService;
    }

    @RequestMapping("fitbitApp/authCode")
    public RedirectView authorization(@RequestParam(value = "code") String code) {

        redisTemplate.opsForValue().set("AuthorizationCode", code);
        LOG.info("Authorization code saved into Redis database and it's ready for use");
        dailyDataService.storeIntradayData();
        return new RedirectView("userData");
    }

}
