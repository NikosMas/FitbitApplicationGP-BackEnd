package com.fitbit.grad.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("downloadProps")
@Getter
@Setter
@NoArgsConstructor
public class DownloadingProperties {

    private String exportUrl;
    private String exportFileName;
}
