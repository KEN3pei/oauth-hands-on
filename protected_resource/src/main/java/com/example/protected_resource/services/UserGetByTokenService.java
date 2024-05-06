package com.example.protected_resource.services;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.ArrayList;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.protected_resource.controllers.responses.ClientFromTokenResponse;
import com.example.protected_resource.util.RsaJwtConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class UserGetByTokenService {
    private final RsaJwtConsumer rsaJwtConsumer;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserGetByTokenService(RsaJwtConsumer rsaJwtConsumer) {
        this.rsaJwtConsumer = rsaJwtConsumer;
    }
 
    public ClientFromTokenResponse execute(String token)
    {
        DecodedJWT decodedJwt = this.rsaJwtConsumer.verifyToken(token);
        String payloadJson = new String(Base64.getUrlDecoder().decode(decodedJwt.getPayload()), StandardCharsets.UTF_8);
        logger.info("Decoded JWT Payload: " + payloadJson);

        Map<String, Object> payloadMap = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            // キーがString、値がObjectのマップに読み込みます。
            payloadMap = mapper.readValue(payloadJson, new TypeReference<Map<String, Object>>(){});
        } catch (Exception e) {
            // エラー！
            e.printStackTrace();
        }

        logger.info("Decoded JWT Payload: " + payloadJson);

        return new ClientFromTokenResponse(
            String.valueOf(payloadMap.get("client_id")),
            (ArrayList)payloadMap.get("client_scope")
        );
    }
}
