package com.novais.fiap.restaurantmanager.controllers;

import com.novais.fiap.restaurantmanager.controllers.requests.ChangePasswordRequest;
import com.novais.fiap.restaurantmanager.controllers.requests.RegisterRequest;
import com.novais.fiap.restaurantmanager.controllers.requests.UpdateUserRequest;
import com.novais.fiap.restaurantmanager.services.users.UserService;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/by-id/{id}")
    public UserViewDTO getUserById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    @GetMapping("/by-name/{name}")
    public List<UserViewDTO> getUserByName(@PathVariable String name) {
        return service.findUserByName(name);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok("User criado com sucesso");
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, Authentication authentication) {

        service.changePassword(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserRequest request, Authentication authentication) {

        service.updateUser(authentication.getName(), request);
        return ResponseEntity.ok().build();
    }

}