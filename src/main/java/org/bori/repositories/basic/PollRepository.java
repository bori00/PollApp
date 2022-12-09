package org.bori.repositories.basic;

import org.bori.entities.Poll;
import org.bori.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PollRepository extends MongoRepository<Poll, String> {
    Optional<Poll> findByUserAndTitle(User user, String title);

    Optional<Poll> findFirstByOrderByCodeDesc();

    Optional<Poll> findPollByCode(Long code);
}
