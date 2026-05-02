package com.restaurantmanager.app.config;

import com.restaurantmanager.users.application.GetUserUseCaseImpl;
import com.restaurantmanager.users.ports.GetUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    public GetUserUseCase getUserUseCase() {
        return new GetUserUseCaseImpl();
    }
}
