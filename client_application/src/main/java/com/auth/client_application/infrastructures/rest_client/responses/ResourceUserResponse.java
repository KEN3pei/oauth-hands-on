package com.auth.client_application.infrastructures.rest_client.responses;

import java.util.ArrayList;
import com.auth.client_application.infrastructures.json_response.JsonResponseInterface;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceUserResponse implements JsonResponseInterface {
    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("scope")
    private ArrayList scope;

    public String getClientId() {
        return this.clientId;
    }

    public ArrayList getScope() {
        return this.scope;
    }
}