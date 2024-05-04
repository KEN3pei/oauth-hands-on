package com.auth.client_application.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CallBackRequest {
    @JsonProperty("code")
    private String code;
    
    @JsonProperty("state")
    private String state;

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
