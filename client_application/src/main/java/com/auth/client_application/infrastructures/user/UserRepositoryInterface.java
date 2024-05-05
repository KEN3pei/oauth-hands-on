package com.auth.client_application.infrastructures.user;

import java.util.List;

import com.auth.client_application.jooq.tables.records.UsersRecord;

public interface UserRepositoryInterface {

    public UsersRecord findByEmail(String email);
    
    public UsersRecord findById(String id);

    public void save(UsersRecord user);

    public List<UsersRecord> findAll();
}
