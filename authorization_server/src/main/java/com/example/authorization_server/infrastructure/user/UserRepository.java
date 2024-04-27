package com.example.authorization_server.infrastructure.user;

import static com.example.authorization_server.jooq.Tables.USERS;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.authorization_server.jooq.tables.Users;
import com.example.authorization_server.jooq.tables.records.UsersRecord;

@Repository
public class UserRepository implements UserRepositoryInterface {

    private final DSLContext create;

    @Autowired
    public UserRepository(DSLContext dslContext) {
        this.create = dslContext;
    }

    public UsersRecord findByEmail(String email) {
        try {
            UsersRecord userRecord = create.selectFrom(USERS)
                    .where(USERS.EMAIL.eq(email))
                    .fetchOne();
            return userRecord;
        } catch (Exception e) {
            return null;
        }
    }

    public UsersRecord save(Users users) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public List<UsersRecord> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
}
