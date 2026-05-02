package com.novais.fiap.restaurantmanager.controllers;

import com.novais.fiap.restaurantmanager.services.users.UserService;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public UserViewDTO getUser(@PathVariable Long id) {
        return service.findUserById(id);
    }

}