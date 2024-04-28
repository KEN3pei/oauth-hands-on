package com.example.authorization_server.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// デシリアライズ時にプロパティが存在しない時にエラーが出ないようにする
@JsonIgnoreProperties(ignoreUnknown=true)
public class AuthorizeRequest {
    @JsonProperty("client_id")
    private String clientId;
    
    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("scope")
    private String scope;
    
    @JsonProperty("response_type")
    private String responseType;
    
    @JsonProperty("state")
    private String state;

    public String getClientId() {
        return clientId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getState() {
        return state;
    }
}
