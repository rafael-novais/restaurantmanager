package com.novais.fiap.restaurantmanager.config.security;

import com.novais.fiap.restaurantmanager.exceptions.InvalidCredentialsException;
import com.novais.fiap.restaurantmanager.repository.users.UserEntity;
import com.novais.fiap.restaurantmanager.repository.users.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity user = repository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Credenciais Inválidas"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

}
