package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with refresh token service requirements
 *
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("refreshtoken")
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenProperties {

    private String grantType;
    private String headerAccept;

}
