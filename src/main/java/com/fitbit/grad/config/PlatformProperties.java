package com.fitbit.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("platformProps")
public class PlatformProperties {

    private String goToPlatformUrl;


    public String getGoToPlatformUrl() {
        return goToPlatformUrl;
    }

    public void setGoToPlatformUrl(String goToPlatformUrl) {
        this.goToPlatformUrl = goToPlatformUrl;
    }
}
