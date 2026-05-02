package com.novais.fiap.restaurantmanager.controllers.requests;

import com.novais.fiap.restaurantmanager.domain.users.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String login;
    private String email;
    private String password;
    private UserRole role;
    private String address;
}
