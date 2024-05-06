package com.auth.client_application.services;

import com.auth.client_application.infrastructures.rest_client.GetResourceUserClient;
import com.auth.client_application.infrastructures.rest_client.responses.ResourceUserResponse;

import org.springframework.stereotype.Service;

@Service
public class GetResourceUserWithTokenService {

    private GetResourceUserClient client;

    public GetResourceUserWithTokenService(GetResourceUserClient client) {
        this.client = client;
    }

    public ResourceUserResponse execute(String token) throws Exception {
        String header = "Bearer " + token;
        return this.client.get(header);
    }
}
