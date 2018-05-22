package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with access token service requirements
 *
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("accesstoken")
@Getter
@Setter
@NoArgsConstructor
public class AuthorizationProperties {

    private String grantType;
    private String redirectUri;
    private String headerAccept;
    private String authCodeUri1;
    private String authCodeUri2;
    private String tokenUrl;
}
