package com.example.authorization_server.services.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordValidator {
    private final BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    public PasswordValidator() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean hashMatch(String plainTxt, String hashedStr)
    {
        return this.passwordEncoder.matches(plainTxt, hashedStr);
    }
}
