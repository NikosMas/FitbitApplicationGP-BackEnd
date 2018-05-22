package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("platformProps")
@Getter
@Setter
@NoArgsConstructor
public class PlatformProperties {

    private String goToPlatformUrl;

}
