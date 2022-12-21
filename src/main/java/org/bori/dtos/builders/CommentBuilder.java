package org.bori.dtos.builders;

import org.bori.dtos.CommentDTO;
import org.bori.dtos.NewCommentDTO;
import org.bori.entities.Comment;
import org.bori.entities.Poll;
import org.bori.entities.User;

import java.time.LocalDateTime;

public class CommentBuilder {

    public static Comment buildComment(NewCommentDTO newCommentDTO, User user, Poll poll) {
        return new Comment(user, poll, LocalDateTime.now(), newCommentDTO.getCommentText());
    }

    public static CommentDTO buildCommentDTO(Comment comment) {
        return new CommentDTO(comment.getMessage(), comment.getUser().username,
                comment.getDateTime());
    }
}
