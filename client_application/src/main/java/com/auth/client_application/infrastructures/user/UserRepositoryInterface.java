package com.auth.client_application.infrastructures.user;

import java.util.List;

import com.auth.client_application.jooq.tables.Users;
import com.auth.client_application.jooq.tables.records.UsersRecord;

public interface UserRepositoryInterface {

    public UsersRecord findByEmail(String email);

    public UsersRecord save(Users users);

    public List<UsersRecord> findAll();
}
