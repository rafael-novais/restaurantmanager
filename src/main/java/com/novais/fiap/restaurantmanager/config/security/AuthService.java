package com.novais.fiap.restaurantmanager.config.security;

import com.novais.fiap.restaurantmanager.controllers.requests.RegisterRequest;
import com.novais.fiap.restaurantmanager.repository.users.UserEntity;
import com.novais.fiap.restaurantmanager.repository.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encode(String password) {
        return passwordEncoder.encode(password);
    }
}