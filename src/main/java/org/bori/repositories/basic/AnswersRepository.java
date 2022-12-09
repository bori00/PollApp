package org.bori.repositories.basic;

import org.bori.entities.Answer;
import org.bori.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnswersRepository extends MongoRepository<Answer, String> {
    List<Answer> findAnswersByUser(User user);
}
