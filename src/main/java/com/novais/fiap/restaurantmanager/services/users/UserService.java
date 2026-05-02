package com.novais.fiap.restaurantmanager.services.users;


import com.novais.fiap.restaurantmanager.mappers.UserMapper;
import com.novais.fiap.restaurantmanager.repository.users.UserRepository;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserViewDTO findUserById(Long id) {
        return userMapper.toUserViewDTO(userRepository.findById(id).orElse(null));
    }
}
