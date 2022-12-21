package org.bori.repositories.basic;

import org.bori.entities.Answer;
import org.bori.entities.Comment;
import org.bori.entities.Poll;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findCommentsByPollOrderByDateTimeDesc(Poll poll);
}
