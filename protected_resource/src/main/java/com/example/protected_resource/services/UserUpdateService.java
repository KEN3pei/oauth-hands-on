package com.example.protected_resource.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.protected_resource.infrastructures.UserRepository;

@Service
public class UserUpdateService {
    private final UserRepository userRepository;

    @Autowired
    public UserUpdateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String id, String introduce)
    {
        this.userRepository.save(id, introduce);
    }
}
