package com.restaurantmanager.users.application;

import com.restaurantmanager.users.ports.GetUserUseCase;
import com.restaurantmanager.users.domain.User;

public class GetUserUseCaseImpl implements GetUserUseCase {

    @Override
    public User execute() {
        return new User("Rafael Modular");
    }
}