package org.bori.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Document("polls")
public class Poll {
    @Transient
    public static final int RECENT_COMMENTS_NR = 5;

    @Id
    private String id;

    private String title;

    private String question;

    private Long code;

    @DBRef
    private User user;

    private List<Option> options  = new ArrayList<>();

    private List<Comment> recent_comments  = new ArrayList<>();

    public Poll(String title, String question, Long code, User user, List<Option> options,
                List<Comment> recent_comments) {
        this.title = title;
        this.question = question;
        this.code = code;
        this.user = user;
        this.options = options;
        this.recent_comments = recent_comments;
    }

    public void addNewComment(Comment comment) {
        comment.setPoll(this);
        recent_comments.add(comment);
        if (this.recent_comments.size() > 5) {
            recent_comments.remove(0);
        }
    }
}
