package com.fitbit.grad.services.authRequests;

import com.fitbit.grad.config.AuthorizationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final AuthorizationProperties authProp;
    private final RedisTemplate<String, String> redisTemplate;

    private final static Logger LOG = LoggerFactory.getLogger("Fitbit application");
    private static String OS = System.getProperty("os.name");

    @Autowired
    public AuthCodeRequestService(AuthorizationProperties authProp, RedisTemplate<String, String> redisTemplate) {
        this.authProp = authProp;
        this.redisTemplate = redisTemplate;
    }

    public void codeRequest() {
        try {
            if (OS.equalsIgnoreCase("linux")) {

                Runtime.getRuntime().exec("xdg-open " + authProp.getAuthCodeUri1() + "client_id="
                        + redisTemplate.opsForValue().get("Client-id") + "&" + authProp.getAuthCodeUri2());
            } else {

                Runtime.getRuntime()
                        .exec("rundll32 url.dll,FileProtocolHandler " + authProp.getAuthCodeUri1() + "client_id="
                                + redisTemplate.opsForValue().get("Client-id") + "&" + authProp.getAuthCodeUri2());
            }

        } catch (IOException e) {
            LOG.error("Something went wrong: ", e);
        }
    }
}
