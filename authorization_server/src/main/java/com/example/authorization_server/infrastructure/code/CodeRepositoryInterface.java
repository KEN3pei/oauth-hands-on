package com.example.authorization_server.infrastructure.code;

import org.jooq.JSON;

import com.example.authorization_server.jooq.tables.records.CodesRecord;

public interface CodeRepositoryInterface {
    public CodesRecord findByCode(String code);
    public void save(String code, JSON query);
    public void delete(String code);
}
