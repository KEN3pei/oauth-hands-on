package com.auth.client_application.infrastructures.rest_client.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenEndpointRequest implements RestClientRequestInterface {
    @JsonProperty("redirect_uri")
    private String redirectUri;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("code")
    private String code;

    public TokenEndpointRequest(
            String redirectUri,
            String grantType,
            String code) {
        this.redirectUri = redirectUri;
        this.grantType = grantType;
        this.code = code;
    }
}