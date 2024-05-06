package com.example.protected_resource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="env")
public class EnvProperties {
    
    private String pubkeypath;

    public String getPubkeypath() {
        return this.pubkeypath;
    }

    public void setPubkeypath(String pubkeypath) {
        this.pubkeypath = pubkeypath;
    }
}
