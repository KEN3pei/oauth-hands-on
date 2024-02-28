package com.example.authorization_server.infrastructure.request;

import static com.example.authorization_server.jooq.Tables.REQUESTS;

import org.jooq.DSLContext;
import org.jooq.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.records.RequestsRecord;

@Repository
public class RequestRepository implements RequestRepositoryInterface {
    private final DSLContext create;

    @Autowired
    public RequestRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public RequestsRecord findByReqId(String reqId)
    {
        try {
            RequestsRecord requestRecord = create.selectFrom(REQUESTS)
                    .where(REQUESTS.REQ_ID.eq(reqId))
                    .fetchOne();
            return requestRecord;
        } catch (Exception e) {
            return null;
        }
    }

    public void save(String reqId, JSON reqJson)
    {
        create.insertInto(REQUESTS, REQUESTS.REQ_ID, REQUESTS.QUERY)
            .values(reqId, reqJson)
            .execute();
    }

    public void delete(String reqId)
    {
        create.delete(REQUESTS).where(REQUESTS.REQ_ID.eq(reqId)).execute();
    }
}
