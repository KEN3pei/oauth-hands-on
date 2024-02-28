package com.example.authorization_server.infrastructure.request;

import org.jooq.JSON;

import com.example.authorization_server.jooq.tables.records.RequestsRecord;

public interface RequestRepositoryInterface {
    public RequestsRecord findByReqId(String reqId);
    public void save(String reqId, JSON reqJson);
    public void delete(String reqId);
}
