package com.example.authorization_server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="env")
public class EnvProperties {
    
    private String prikeypath;

    public String getPrikeypath() {
        return this.prikeypath;
    }

    public void setPrikeypath(String prikeypath) {
        this.prikeypath = prikeypath;
    }

}
