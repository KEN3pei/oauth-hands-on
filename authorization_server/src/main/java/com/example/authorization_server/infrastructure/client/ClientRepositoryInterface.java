package com.example.authorization_server.infrastructure.client;

import com.example.authorization_server.jooq.tables.records.ClientsRecord;

public interface ClientRepositoryInterface {
    public ClientsRecord findByClientId(String clientId);
}
