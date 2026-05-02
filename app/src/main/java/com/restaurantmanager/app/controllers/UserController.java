package com.restaurantmanager.app.controllers;

import com.restaurantmanager.users.ports.GetUserUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GetUserUseCase useCase;

    public UserController(GetUserUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public String getUser() {
        return useCase.execute().getName();
    }

}