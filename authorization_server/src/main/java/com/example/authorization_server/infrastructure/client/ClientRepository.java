package com.example.authorization_server.infrastructure.client;

import static com.example.authorization_server.jooq.Tables.CLIENTS;

import java.util.Optional;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.records.ClientsRecord;

@Repository
public class ClientRepository implements ClientRepositoryInterface {
    
    private final DSLContext create;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClientRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public Optional<ClientsRecord> findByClientId(String clientId)
    {
        ClientsRecord clientRecord = create.selectFrom(CLIENTS)
                .where(CLIENTS.CLIENT_ID.eq(clientId))
                .fetchOne();
        return Optional.ofNullable(clientRecord);
    }
}
