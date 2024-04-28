package com.example.authorization_server.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// デシリアライズ時にプロパティが存在しない時にエラーが出ないようにする
@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenRequest {
    @JsonProperty("code")
    private String code;

    @JsonProperty("grant_type")
    private String grantType;
    
    @JsonProperty("redirect_uri")
    private String redirectUri;

    public String getCode() {
        return code;
    }

    public String getGrantType() {
        return grantType;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
}
