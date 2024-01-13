package com.example.authorization_server.infrastructure;

import java.util.List;

import com.example.authorization_server.jooq.tables.Users;
import com.example.authorization_server.jooq.tables.records.UsersRecord;

public interface UserRepositoryInterface {

    public UsersRecord findByEmail(String email);
    public UsersRecord save(Users users);
    public List<UsersRecord> findAll();
}
