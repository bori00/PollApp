package org.bori.services;

import org.bori.entities.*;
import org.bori.repositories.basic.AnswersRepository;
import org.bori.repositories.basic.CommentRepository;
import org.bori.repositories.basic.PollRepository;
import org.bori.repositories.basic.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {

    @Autowired
    TestService(UserRepository userRepository, PollRepository pollRepository,
                AnswersRepository answerRepository, CommentRepository commentRepository) {

        // save a couple of customers
//        User user = new User("Alice", "passA");
//        User savedUser = userRepository.save(user);
//        // repository.save(new User("Bob", "passB"));
//
//        Option option1 = new Option("Container", 1);
//
//        Poll poll = new Poll("Azure Method", "Which Azure method do you prefer?", savedUser,
//                new ArrayList<>(List.of(
//                        option1,
//                        new Option("SQL Server ", 2)
//                )), new ArrayList<>());
//
//        Poll savedPoll = pollRepository.save(poll);
//
//        Comment comment = new Comment(savedUser, savedPoll, LocalDateTime.now(), "Message 1");
//        savedPoll.addNewComment(comment);
//        savedPoll = pollRepository.save(savedPoll);
//
//        Answer answer = new Answer(savedUser, savedPoll, 0);
//        answerRepository.save(answer);
//
//        Comment oldComment = new Comment(savedUser, savedPoll, LocalDateTime.now(), "Message Old");
//        commentRepository.save(oldComment);
//
//        // fetch all customers
//        System.out.println("Customers found with findAll():");
//        System.out.println("-------------------------------");
//        for (User user1 : userRepository.findAll()) {
//            System.out.println(user1);
//        }
//        System.out.println();
    }
}
