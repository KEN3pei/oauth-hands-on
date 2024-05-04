package com.auth.client_application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.client_application.controllers.requests.CallBackRequest;
import com.auth.client_application.infrastructures.client.ClientRepository;
import com.auth.client_application.infrastructures.rest_client.TokenEndpointClient;
import com.auth.client_application.infrastructures.rest_client.requests.TokenEndpointRequest;
import com.auth.client_application.infrastructures.rest_client.responses.TokenEndpointResponse;

@Service
public class TokenService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ClientRepository clientRepository;
    private final TokenEndpointClient tokenEndpointClient;

    @Autowired
    public TokenService(
            ClientRepository clientRepository,
            TokenEndpointClient tokenEndpointClient) {
        this.clientRepository = clientRepository;
        this.tokenEndpointClient = tokenEndpointClient;
    }

    public TokenEndpointResponse execute(CallBackRequest request) throws Exception {
        // 認証中のユーザーが持つstateを取得
        String clientState = this.clientRepository.findByClientId("1");
        logger.info("<<< FIND CLIENT >>>");
        // stateの一致確認
        if (!request.getState().equals(clientState)) {
            throw new Exception("invalid state request");
        }
        // POST /token_endpoint
        String redirectUri = "http://localhost:8002/callback";
        String clientPassword = "password";
        String grantType = "authorization_code";
        TokenEndpointRequest body = new TokenEndpointRequest(
                redirectUri,
                grantType,
                request.getCode());
        return this.tokenEndpointClient.post(body, "1", clientPassword);
    }
}
