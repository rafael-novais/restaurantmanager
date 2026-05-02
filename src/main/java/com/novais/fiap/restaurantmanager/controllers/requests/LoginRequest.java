package com.novais.fiap.restaurantmanager.controllers.requests;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
