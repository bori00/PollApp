package org.bori.repositories.custom.impl;

import org.bori.entities.Answer;
import org.bori.entities.Poll;
import org.bori.entities.User;
import org.bori.repositories.basic.AnswersRepository;
import org.bori.repositories.custom.CustomPollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomPollRepositoryImpl implements CustomPollRepository {

    private final MongoTemplate mongotemplate;

    private final AnswersRepository answersRepository;

    @Autowired
    public CustomPollRepositoryImpl(MongoTemplate mongoTemplate,
                                    AnswersRepository answersRepository) {
        this.mongotemplate = mongoTemplate;
        this.answersRepository = answersRepository;
    }

    @Override
    public List<Poll> findPollsByJoinedUser(User user) {
        return answersRepository.findAnswersByUser(user).stream().map(Answer::getPoll).distinct().collect(Collectors.toList());
    }
}
