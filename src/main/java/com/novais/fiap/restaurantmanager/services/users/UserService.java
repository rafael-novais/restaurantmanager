package com.novais.fiap.restaurantmanager.services.users;


import com.novais.fiap.restaurantmanager.config.security.AuthService;
import com.novais.fiap.restaurantmanager.controllers.requests.ChangePasswordRequest;
import com.novais.fiap.restaurantmanager.controllers.requests.RegisterRequest;
import com.novais.fiap.restaurantmanager.controllers.requests.UpdateUserRequest;
import com.novais.fiap.restaurantmanager.exceptions.InsertToDatabaseException;
import com.novais.fiap.restaurantmanager.exceptions.ResourceNotFoundException;
import com.novais.fiap.restaurantmanager.mappers.UserMapper;
import com.novais.fiap.restaurantmanager.repository.users.UserEntity;
import com.novais.fiap.restaurantmanager.repository.users.UserRepository;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthService authService;

    public UserViewDTO findUserById(Long id) {
        return userMapper.toUserViewDTO(
                userRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário %s não encontrado!", id))));
    }

    public List<UserViewDTO> findUserByName(String name) {
        return userMapper.toUserViewDTOList(
                userRepository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Nenhum usuário %s encontrado!", name))));
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException(String.format("Nenhum usuário %s encontrado!", email)));
    }

    public void register(RegisterRequest request) {
        UserEntity user = new UserEntity();

        user.setPassword(authService.encode(request.getPassword()));

        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setRole(request.getRole());
        user.setAddress(request.getAddress());
        user.setName(request.getName());
        user.setLastModified(new Date());

        try {
            userRepository.save(user);
        }catch (Exception ex) {
            throw new InsertToDatabaseException(ex.getMessage());
        }

    }

    public void remove(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Usuário %s não encontrado!".formatted(id)
            );
        }
        userRepository.deleteById(id);
    }

    public void changePassword(String email, ChangePasswordRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário %s não encontrado!", email)));

        user.setPassword(authService.encode(user, request));
        user.setLastModified(new Date());
        userRepository.save(user);
    }

    public void updateUser(String email, UpdateUserRequest request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Usuário %s não encontrado!", email)));

        user.setEmail(isStringValid(request.getEmail()) ? request.getEmail() : email);
        user.setLogin(isStringValid(request.getLogin()) ? request.getLogin() : user.getLogin());
        user.setRole(request.getRole() != null ? request.getRole() : user.getRole());
        user.setAddress(isStringValid(request.getAddress()) ? request.getAddress() : user.getAddress());
        user.setName(isStringValid(request.getName()) ? request.getName() : user.getName());
        user.setLastModified(new Date());
        try {
            userRepository.save(user);
        }catch (Exception ex) {
            throw new InsertToDatabaseException(ex.getMessage());
        }
    }

    private boolean isStringValid(String s) {
        return s != null && !s.trim().isBlank() && !s.trim().isEmpty();
    }
}
