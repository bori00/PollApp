package org.bori.services.authentication;

import org.bori.dtos.NewUserDTO;
import org.bori.dtos.builders.UserBuilder;
import org.bori.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Factory class that builds a user from a given NewUserDTO.
 */
public class UserFactory {

    public static User buildUser(NewUserDTO userDTO,
                                 PasswordEncoder passwordEncoder) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return UserBuilder.toEntity(userDTO);
    }
}
