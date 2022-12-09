package org.bori.controllers;

import org.bori.controllers.handlers.exceptions.model.authentication.DuplicateUsernameException;
import org.bori.dtos.NewPollDTO;
import org.bori.dtos.PollDTO;
import org.bori.services.poll_management.PollManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("poll-management")
public class PollManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PollManagementController.class);

    private final PollManagementService pollManagementService;

    @Autowired
    public PollManagementController(PollManagementService pollManagementService) {
        this.pollManagementService = pollManagementService;
    }

    @PostMapping("/close-poll")
    @ResponseStatus(HttpStatus.OK)
    void closePoll(@Valid @RequestBody String pollId) throws DuplicateUsernameException {
        LOGGER.info(String.format("REQUEST - /close, for poll %s",
                pollId));
        // ...
    }

    @PostMapping("/create-poll")
    @ResponseStatus(HttpStatus.OK)
    void createPoll(@Valid @RequestBody NewPollDTO newPollDTO) throws DuplicateUsernameException {
        LOGGER.info(String.format("REQUEST - /create-poll with title %s",
                newPollDTO.getTitle()));
        pollManagementService.addNewPoll(newPollDTO);
    }

    @GetMapping("/get-my-polls")
    List<PollDTO> getMyPolls() {
        LOGGER.info("REQUEST - /getMyPolls");
        return pollManagementService.getMyPolls();
    }

    @PostMapping("/join-poll")
    void joinPoll(@Valid @RequestBody Long code) {
        LOGGER.info("REQUEST - /join-poll");
        pollManagementService.joinPoll(code);
    }
}
