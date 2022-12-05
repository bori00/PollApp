package org.bori.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("users")
public class User {

    @Id
    public String id;

    public String username;

    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
