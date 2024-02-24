package com.example.authorization_server.infrastructure.code;

import static com.example.authorization_server.jooq.Tables.CODES;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.Codes;
import com.example.authorization_server.jooq.tables.records.CodesRecord;

@Repository
public class CodeRepository implements CodeRepositoryInterface{
    private final DSLContext create;

    @Autowired
    public CodeRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public CodesRecord findByCode(String code)
    {
        try {
            CodesRecord codesRecord = create.selectFrom(CODES)
                    .where(CODES.CODE.eq(code))
                    .fetchOne();
            return codesRecord;
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Codes codes)
    {
        create.insertInto(CODES, CODES.CODE, CODES.QUERY)
            .values(codes.CODE, codes.QUERY)
            .execute();
    }

    public void delete(String code)
    {
        create.delete(CODES).where(CODES.CODE.eq(code)).execute();
    }
}
