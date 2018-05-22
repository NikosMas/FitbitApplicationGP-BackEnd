package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * properties for api urls
 *
 * @author nikosmas, alex_kak
 */
@ConfigurationProperties("fitbitApiUrls")
@Getter
@Setter
@NoArgsConstructor
public class FitbitApiUrlProperties {

    private String timeInBedUrl;
    private String minutesAsleepUrl;
    private String minutesAwakeUrl;
    private String toFallAsleepUrl;
    private String afterWakeUpUrl;
    private String efficiencyUrl;
    private String stepsUrl;
    private String floorsUrl;
    private String distanceUrl;
    private String caloriesUrl;
    private String heartUrl;
    private String profileUrl;
    private String lifetimeUrl;
    private String frequenceUrl;

}
