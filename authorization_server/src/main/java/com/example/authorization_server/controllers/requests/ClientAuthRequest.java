package com.example.authorization_server.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ClientAuthRequest {
    @JsonProperty("request_id")
    private String requestId;
    
    @JsonProperty("approve")
    private Boolean approve;

    public String getRequestId() {
        return requestId;
    }

    public Boolean getApprove() {
        return approve;
    }
}
