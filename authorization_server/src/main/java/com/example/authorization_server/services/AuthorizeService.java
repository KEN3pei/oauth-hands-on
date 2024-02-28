package com.example.authorization_server.services;

import java.util.HashMap;
import java.util.Map;

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

@Service
public class AuthorizeService {

    private final ClientRepositoryInterface clientRepository;
    private final RequestRepository requestRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AuthorizeService(
        ClientRepositoryInterface clientRepository,
        RequestRepository requestRepository
    ) {
        this.clientRepository = clientRepository;
        this.requestRepository = requestRepository;
    }

    public Object[] execute(AuthorizeRequest request) throws Exception {
        ClientsRecord client = clientRepository.findByClientId(request.getClientId());
        String requestRedirectUri = request.getRedirectUri();
        String clientRedirectUri = client.getRedirectUri();

        if (!requestRedirectUri.equals(clientRedirectUri)) {
            logger.info("invalid request throw Exception");
            throw new Exception("invalid request");
        }

        String reqId = GenerateUUID.generate();
        logger.info(" >>> GenerateUUID >>> ");

        JSON json = this.toJson(request);
        requestRepository.save(reqId, json);

        Object[] res = {client, reqId, request.getScope()};
        return res;
    }

    private JSON toJson(AuthorizeRequest request) throws JsonProcessingException
    {
        Map<String, String> map = new HashMap<String, String>();

        map.put("client_id", request.getClientId());
        map.put("redirect_uri", request.getRedirectUri());
        map.put("client_secret", request.getClientSecret());
        map.put("scope", request.getScope());
        map.put("response_type", request.getResponseType());
        map.put("state", request.getState());
    
        String json = new ObjectMapper().writeValueAsString(map);
        logger.info(json);

        // 参照: https://www.jooq.org/javadoc/latest/org.jooq/org/jooq/JSON.html#data()
        return JSON.json(json);
    }
}
