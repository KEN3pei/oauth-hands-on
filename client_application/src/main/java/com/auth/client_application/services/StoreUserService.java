package com.auth.client_application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth.client_application.infrastructures.user.UserRepository;
import com.auth.client_application.jooq.tables.records.UsersRecord;

@Service
public class StoreUserService {
    private final UserRepository userRepository;

    @Autowired
    public StoreUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String userId, String accessToken)
    {
        UsersRecord user = this.userRepository.findById(userId);
        user.setAccessToken(accessToken);
        this.userRepository.save(user);
    }
}
