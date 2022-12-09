package org.bori.repositories.custom;

import org.bori.entities.Poll;
import org.bori.entities.User;

import java.util.List;

public interface CustomPollRepository {
    List<Poll> findPollsByJoinedUser(User user);
}
