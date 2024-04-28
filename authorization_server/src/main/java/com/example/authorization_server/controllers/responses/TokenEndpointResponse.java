package com.example.authorization_server.controllers.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// デシリアライズ時にプロパティが存在しない時にエラーが出ないようにする
@JsonIgnoreProperties(ignoreUnknown=true)
public class TokenEndpointResponse {
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("token_type")
    private String tokenType;

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }
}
