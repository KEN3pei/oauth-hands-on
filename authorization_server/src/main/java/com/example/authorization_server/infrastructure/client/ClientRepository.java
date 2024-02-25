package com.example.authorization_server.infrastructure.client;

import static com.example.authorization_server.jooq.Tables.CLIENTS;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.records.ClientsRecord;

@Repository
public class ClientRepository implements ClientRepositoryInterface {
    
    private final DSLContext create;

    @Autowired
    public ClientRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public ClientsRecord findByClientId(String clientId)
    {
        try {
            ClientsRecord clientRecord = create.selectFrom(CLIENTS)
                    .where(CLIENTS.CLIENT_ID.eq(clientId))
                    .fetchOne();
            return clientRecord;
        } catch (Exception e) {
            return null;
        }
    }
}
