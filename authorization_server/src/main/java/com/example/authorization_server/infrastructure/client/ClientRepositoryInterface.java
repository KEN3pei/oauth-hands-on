package com.example.authorization_server.infrastructure.client;

import java.util.Optional;

import com.example.authorization_server.jooq.tables.records.ClientsRecord;

public interface ClientRepositoryInterface {
    public Optional<ClientsRecord> findByClientId(String clientId);
}
