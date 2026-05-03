package com.novais.fiap.restaurantmanager.config.security;

import com.novais.fiap.restaurantmanager.controllers.requests.ChangePasswordRequest;
import com.novais.fiap.restaurantmanager.controllers.requests.RegisterRequest;
import com.novais.fiap.restaurantmanager.exceptions.InvalidCredentialsException;
import com.novais.fiap.restaurantmanager.exceptions.ValidationException;
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

    public String encode(UserEntity user, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Credenciais inválidas");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Nova senha deve ser diferente da atual");
        }
        return encode(request.getNewPassword());
    }
}