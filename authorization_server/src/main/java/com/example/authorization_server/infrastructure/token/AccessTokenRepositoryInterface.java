package com.example.authorization_server.infrastructure.token;

import com.example.authorization_server.jooq.tables.records.TokenRecord;

public interface AccessTokenRepositoryInterface {
    public void save(TokenRecord token);
}
