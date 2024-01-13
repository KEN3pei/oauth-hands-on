package com.example.protected_resource.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.protected_resource.infrastructures.UserRepository;
import com.example.protected_resource.jooq.tables.records.UsersRecord;

@Service
public class UserGetAllService {
    private final UserRepository userRepository;

    @Autowired
    public UserGetAllService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UsersRecord> execute()
    {
        return this.userRepository.findAll();
    }
}
