package com.example.protected_resource.infrastructures;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements UserRepositoryInterface {

    // private final DSLContext create;

    // @Autowired
    // public UserRepository(DSLContext dslContext) {
    //     this.create = dslContext;
    // }

    // @Override
    // public UsersRecord findByEmail(String email) {
    //     try {
    //         UsersRecord userRecord = create.selectFrom(USERS)
    //                 .where(USERS.EMAIL.eq(email))
    //                 .fetchOne();
    //         return userRecord;
    //     } catch (Exception e) {
    //         return null;
    //     }
    // }

    // @Override
    // public void save(String id, String introduce) {
    //     this.create.update(USERS)
    //         .set(USERS.INTRODUCE, introduce)
    //         .where(USERS.ID.eq(id))
    //         .execute();
    // }

    // @Override
    // public List<UsersRecord> findAll() {
    //     List<UsersRecord> result = create.selectFrom(USERS).fetch();
    //     return result;
    // }
}
