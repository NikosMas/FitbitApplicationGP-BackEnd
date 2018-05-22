package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * property configuration associated with e-mail service requirements
 *
 * @author nikos_mas, alex_kak
 */

@ConfigurationProperties("mailInfo")
@Getter
@Setter
@NoArgsConstructor
public class MailInfoProperties {

    private String fileName;
    private String username;
    private String password;
    private String sendFrom;
    private String mailSmtpStartEnable;
    private String mailSmtpAuth;
    private String mailSmtpHost;
    private String mailSmtpPort;
    private String mailSubject;

}
