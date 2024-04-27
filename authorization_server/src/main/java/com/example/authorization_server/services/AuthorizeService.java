package com.example.authorization_server.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.jooq.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorization_server.controllers.requests.AuthorizeRequest;
import com.example.authorization_server.infrastructure.client.ClientRepositoryInterface;
import com.example.authorization_server.infrastructure.request.RequestRepository;
import com.example.authorization_server.jooq.tables.records.ClientsRecord;
import com.example.authorization_server.services.util.GenerateUUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthorizeService {

    private final ClientRepositoryInterface clientRepository;
    private final RequestRepository requestRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthorizeService(
        ClientRepositoryInterface clientRepository,
        RequestRepository requestRepository
    ) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Object[] execute(AuthorizeRequest request) throws Exception {
        Optional<ClientsRecord> optClient = clientRepository.findByClientId(request.getClientId());

        ClientsRecord client;
        if (optClient.isPresent()){
            // nullでない場合
            client = optClient.get();
            logger.info("<<< FIND CLIENT BY ID >>>", client);
        } else {
            // nullの場合
            throw new Exception("client is not found");
        }
        // passwordチェック
        if (!this.hashMatch(request.getClientSecret(), client.getSecret())) {
            logger.info(client.getSecret());
            logger.info(request.getClientSecret());
            throw new Exception("invalid client secret");
        }

        String requestRedirectUri = request.getRedirectUri();
        String clientRedirectUri = client.getRedirectUri();

        if (!requestRedirectUri.equals(clientRedirectUri)) {
            throw new Exception("invalid redirectUri in request");
        }

        String reqId = GenerateUUID.generate();
        logger.info("<<< GENERATED UUID >>>");

        JSON json = this.toJson(request);
        requestRepository.save(reqId, json);
        logger.info("<<< SAVED REQUEST request id is :"+ reqId);

        Object[] res = {client, reqId, request.getScope()};
        return res;
    }

    private JSON toJson(AuthorizeRequest request) throws JsonProcessingException
    {
        Map<String, String> map = new HashMap<String, String>();

        map.put("client_id", request.getClientId());
        map.put("redirect_uri", request.getRedirectUri());
        // map.put("client_secret", request.getClientSecret());
        map.put("scope", request.getScope());
        map.put("response_type", request.getResponseType());
        map.put("state", request.getState());
    
        String json = new ObjectMapper().writeValueAsString(map);
        logger.info(json);

        // 参照: https://www.jooq.org/javadoc/latest/org.jooq/org/jooq/JSON.html#data()
        return JSON.json(json);
    }

    private boolean hashMatch(String plainTxt, String hashedStr)
    {
        return this.passwordEncoder.matches(plainTxt, hashedStr);
    }
}
