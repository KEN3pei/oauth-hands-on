package com.example.authorization_server.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenRequest {
    @JsonProperty("code")
    private String code;

    @JsonProperty("client_id")
    private String clientId;
    
    @JsonProperty("pass")
    private String pass;

    public String getCode() {
        return code;
    }

    public String getClientId() {
        return clientId;
    }

    public String getPass() {
        return pass;
    }
}
