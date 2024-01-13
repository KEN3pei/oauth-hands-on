package com.example.protected_resource.infrastructures;

import java.util.List;

import com.example.protected_resource.jooq.tables.records.UsersRecord;

public interface UserRepositoryInterface {

    public UsersRecord findByEmail(String email);

    public void save(String id, String introduce);
    
    public List<UsersRecord> findAll();
}
