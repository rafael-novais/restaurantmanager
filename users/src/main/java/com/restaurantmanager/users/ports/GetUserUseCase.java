package com.restaurantmanager.users.ports;

import com.restaurantmanager.users.domain.User;

public interface GetUserUseCase {
    User execute();
}