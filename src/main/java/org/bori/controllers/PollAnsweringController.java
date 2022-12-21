package org.bori.controllers;

import org.apache.tomcat.jni.Poll;
import org.bori.dtos.AnswerDTO;
import org.bori.dtos.NewVoteDTO;
import org.bori.dtos.PollDTO;
import org.bori.services.poll_answering.PollAnsweringService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("poll-answering")
public class PollAnsweringController {

    private final PollAnsweringService pollAnsweringService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PollAnsweringController.class);

    @Autowired
    public PollAnsweringController(PollAnsweringService pollAnsweringService) {
        this.pollAnsweringService = pollAnsweringService;
    }

    @GetMapping("/get-my-answer-for-poll")
    public AnswerDTO getMyAnswerForPoll(@RequestParam Long pollJoiningCode) {
        LOGGER.info("REQUEST - /get-my-answer-for-poll");
        return pollAnsweringService.findCurrentUsersAnswer(pollJoiningCode);
    }

    @GetMapping("/get-poll")
    public PollDTO getPoll(@RequestParam Long pollJoiningCode) {
        LOGGER.info("REQUEST - /get-poll");
        return pollAnsweringService.findPoll(pollJoiningCode);
    }

    @PostMapping("/cast-vote-for-poll")
    void joinPoll(@Valid @RequestBody NewVoteDTO newVoteDTO) throws SQLException {
        LOGGER.info("REQUEST - /cast-vote-for-poll");
        pollAnsweringService.castNewVote(newVoteDTO.getPollJoinCode(),
                newVoteDTO.getSelectedOptionIndex());
    }

    @PostMapping("/remove-vote-for-poll")
    void joinPoll(@Valid @RequestBody Long pollJoiningCode) throws SQLException {
        LOGGER.info("REQUEST - /cast-vote-for-poll");
        pollAnsweringService.removeVote(pollJoiningCode);
    }

}
