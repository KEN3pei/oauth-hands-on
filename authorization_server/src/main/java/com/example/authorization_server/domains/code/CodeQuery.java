package com.example.authorization_server.domains.code;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CodeQuery {
    @JsonProperty("scope")
    public String scope;
    
    @JsonProperty("state")
    public String state;

    @JsonProperty("client_id")
    public String clientId;

    @JsonProperty("redirect_uri")
    public String redirectUri;

    @JsonProperty("client_secret")
    public String clientSecret;

    @JsonProperty("response_type")
    public String responseType;
}
