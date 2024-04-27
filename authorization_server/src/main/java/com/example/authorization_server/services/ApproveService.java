package com.example.authorization_server.services;

import java.util.Map;

import org.jooq.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.authorization_server.controllers.requests.ClientAuthFormRequest;
import com.example.authorization_server.infrastructure.code.CodeRepository;
import com.example.authorization_server.infrastructure.request.RequestRepository;
import com.example.authorization_server.jooq.tables.records.RequestsRecord;
import com.example.authorization_server.services.util.GenerateUUID;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApproveService {

    private final RequestRepository requestRepository;
    private final CodeRepository codeRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ApproveService(
        RequestRepository requestRepository,
        CodeRepository codeRepository
    ) {
        this.requestRepository = requestRepository;
        this.codeRepository = codeRepository;
    }

    public Object[] execute(ClientAuthFormRequest request) throws Exception {
        if (request.getApprove() == null) {
            throw new Exception("doesn't approved this request");
        }
        String reqId = request.getReqId();

        RequestsRecord requestRecord = this.requestRepository.findByReqId(reqId);
        logger.info("<<< FIND REQUEST BY reqId :"+ reqId+ " >>>");
        this.requestRepository.delete(reqId);
        logger.info("<<< DELETE REQUEST >>>");

        JSON query = requestRecord.getQuery();
        logger.info("<<< PAST JSON REQUEST: "+ query.toString());

        Map<String, String> mapQuery = null;
        ObjectMapper mapper = new ObjectMapper();
        mapQuery = mapper.readValue(query.toString(), new TypeReference<Map<String, String>>(){});

        String resType = mapQuery.get("response_type");
        if (!resType.equals("code")) {
            throw new Exception("unsupported response type error");
        }

        String authCode = GenerateUUID.generate();
        this.codeRepository.save(authCode, query);
        logger.info("<<< SAVE REQUEST QUERY WITH AUTHCODE KEY >>>");

        Object[] res = {authCode, mapQuery};
        return res;
    }
}
