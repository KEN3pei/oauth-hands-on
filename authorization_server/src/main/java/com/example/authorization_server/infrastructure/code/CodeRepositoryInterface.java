package com.example.authorization_server.infrastructure.code;

import com.example.authorization_server.jooq.tables.Codes;
import com.example.authorization_server.jooq.tables.records.CodesRecord;

public interface CodeRepositoryInterface {
    public CodesRecord findByCode(String code);
    public void save(Codes codes);
    public void delete(String code);
}
