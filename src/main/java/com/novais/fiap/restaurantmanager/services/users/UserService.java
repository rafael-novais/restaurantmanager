package com.novais.fiap.restaurantmanager.services.users;


import com.novais.fiap.restaurantmanager.config.security.AuthService;
import com.novais.fiap.restaurantmanager.controllers.requests.RegisterRequest;
import com.novais.fiap.restaurantmanager.exceptions.InsertToDatabaseException;
import com.novais.fiap.restaurantmanager.exceptions.ResourceNotFoundException;
import com.novais.fiap.restaurantmanager.mappers.UserMapper;
import com.novais.fiap.restaurantmanager.repository.users.UserEntity;
import com.novais.fiap.restaurantmanager.repository.users.UserRepository;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
