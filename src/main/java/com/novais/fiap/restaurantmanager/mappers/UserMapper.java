package com.novais.fiap.restaurantmanager.mappers;

import com.novais.fiap.restaurantmanager.repository.users.UserEntity;
import com.novais.fiap.restaurantmanager.services.users.dto.UserViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "name", target = "name")
    UserViewDTO toUserViewDTO(UserEntity entity);

    @Mapping(source = "name", target = "name")
    List<UserViewDTO> toUserViewDTOList(List<UserEntity> entity);
}
