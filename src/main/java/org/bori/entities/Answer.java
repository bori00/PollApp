package org.bori.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("answers")
public class Answer {
    @org.springframework.data.annotation.Id
    private String Id;

    @DBRef
    public User user;

    @DBRef
    public Poll poll;

    @Nullable
    public Integer selectedOptionIndex;

    public Answer(User user, Poll poll, Integer selectedOptionIndex) {
        this.user = user;
        this.poll = poll;
        this.selectedOptionIndex = selectedOptionIndex;
    }


}
