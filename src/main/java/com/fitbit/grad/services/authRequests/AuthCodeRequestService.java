package com.fitbit.grad.services.authRequests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Service about receiving authorization code from fitbit api
 *
 * @author nikos_mas, alex_kak
 */

@Service
public class AuthCodeRequestService {

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
    private static String OS = System.getProperty("os.name");
    private final RedisTemplate<String, String> redisTemplate;
    private final Environment env;

    @Autowired
    public AuthCodeRequestService(Environment env, RedisTemplate<String, String> redisTemplate) {
        this.env = env;
        this.redisTemplate = redisTemplate;
    }

    public void codeRequest() {
        try {
            if (OS.equalsIgnoreCase("linux")) {

                Runtime.getRuntime().exec("xdg-open " + env.getProperty("accesstoken.authCodeUri1") + "client_id="
                        + redisTemplate.opsForValue().get("Client-id") + "&" + env.getProperty("accesstoken.authCodeUri2"));
            } else {

                Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + env.getProperty("accesstoken.authCodeUri1") + "client_id="
                                + redisTemplate.opsForValue().get("Client-id") + "&" + env.getProperty("accesstoken.authCodeUri2"));
            }

        } catch (IOException e) {
            LOG.error("Something went wrong: ", e);
        }
    }
}
