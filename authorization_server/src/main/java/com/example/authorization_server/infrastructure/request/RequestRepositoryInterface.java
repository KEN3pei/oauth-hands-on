package com.example.authorization_server.infrastructure.request;

import com.example.authorization_server.jooq.tables.Requests;
import com.example.authorization_server.jooq.tables.records.RequestsRecord;

public interface RequestRepositoryInterface {
    public RequestsRecord findByReqId(String reqId);
    public void save(Requests request);
    public void delete(String reqId);
}
