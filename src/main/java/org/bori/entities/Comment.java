package org.bori.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("comments")
//@Embeddable
public class Comment {
    @org.springframework.data.annotation.Id
    private String Id;

    @DBRef
    public User user;

    @DBRef
    public Poll poll;

    public LocalDateTime dateTime;

    public String message;

    public Comment(User user, Poll poll, LocalDateTime dateTime, String message) {
        this.user = user;
        this.poll = poll;
        this.dateTime = dateTime;
        this.message = message;
    }
}
