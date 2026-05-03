package com.novais.fiap.restaurantmanager.controllers.requests;

import com.novais.fiap.restaurantmanager.domain.users.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserRequest {
    private String name;
    private String login;
    @Email(message = "Email inválido")
    private String email;
    private UserRole role;
    private String address;
}
