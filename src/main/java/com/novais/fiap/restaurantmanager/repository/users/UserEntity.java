package com.novais.fiap.restaurantmanager.repository.users;

import com.novais.fiap.restaurantmanager.domain.users.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter @Setter
public class UserEntity {

    @Id
    private Long id;
    private String name;
    private String email;
    private String login;
    private String salt;
    private String password;
    private Date lastModified;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
