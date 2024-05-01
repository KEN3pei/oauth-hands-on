package com.example.authorization_server.infrastructure.token;

import static com.example.authorization_server.jooq.Tables.TOKEN;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.records.TokenRecord;

@Repository
public class AccessTokenRepository implements AccessTokenRepositoryInterface {
    private final DSLContext create;

    @Autowired
    public AccessTokenRepository(DSLContext dslContext) {
        this.create = dslContext;
    }
    
    public void save(TokenRecord token)
    {
        create.insertInto(TOKEN, TOKEN.ACCESS_TOKEN, TOKEN.CLIENT_ID, TOKEN.QUERY)
            .values(token.getAccessToken(), token.getClientId(), token.getQuery())
            .execute();
    }
}
