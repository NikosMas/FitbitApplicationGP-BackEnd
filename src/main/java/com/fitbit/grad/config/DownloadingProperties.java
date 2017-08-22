package com.fitbit.grad.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("downloadProps")
public class DownloadingProperties {

    private String exportUrl;
    private String exportFileName;

    public String getExportUrl() {
        return exportUrl;
    }

    public void setExportUrl(String exportUrl) {
        this.exportUrl = exportUrl;
    }

    public String getExportFileName() {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName) {
        this.exportFileName = exportFileName;
    }
}
