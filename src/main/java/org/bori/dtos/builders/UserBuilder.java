package org.bori.dtos.builders;

import org.bori.dtos.NewUserDTO;
import org.bori.dtos.UserDTO;
import org.bori.entities.User;

public class UserBuilder {
    public static User toEntity(NewUserDTO newUserDTO) {
        return new User(newUserDTO.getUserName(), newUserDTO.getPassword());
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword());
    }
}
