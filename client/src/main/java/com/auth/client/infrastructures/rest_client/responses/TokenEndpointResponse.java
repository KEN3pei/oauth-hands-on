package com.auth.client.infrastructures.rest_client.responses;

import com.auth.client.infrastructures.json_response.JsonResponseInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenEndpointResponse implements JsonResponseInterface {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;
}