package com.auth.client_application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.client_application.infrastructures.user.UserRepository;
import com.auth.client_application.jooq.tables.records.UsersRecord;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        try {
            UsersRecord user = this.userRepository.findByEmail(email);
            return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException("ユーザー名かパスワードが間違っています");
        }
    }
}
