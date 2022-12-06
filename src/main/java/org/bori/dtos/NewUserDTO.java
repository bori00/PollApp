package org.bori.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class NewUserDTO {

    @NotBlank(message = "The username cannot be blank.")
    @Size(min = 3, max = 30, message = "The username should have a length between 3 and " +
            "30")
    private String userName;

    @NotBlank(message = "The password cannot be blank.")
    @Size(min = 3, max = 100, message = "The password should have a length between 3 " +
            "and 100.")
    private String password;
}
