package org.bori.controllers;

import org.bori.dtos.CommentDTO;
import org.bori.dtos.NewCommentDTO;
import org.bori.dtos.NewVoteDTO;
import org.bori.services.poll_answering.PollAnsweringService;
import org.bori.services.poll_discussion.PollDiscussionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("poll-discussion")
public class PollDiscussionController {

    private final PollDiscussionService pollDiscussionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PollDiscussionController.class);

    @Autowired
    public PollDiscussionController(PollDiscussionService pollDiscussionService) {
        this.pollDiscussionService = pollDiscussionService;
    }

    @PostMapping("/add-comment-to-poll")
    void joinPoll(@Valid @RequestBody NewCommentDTO newCommentDTO) throws SQLException {
        LOGGER.info("REQUEST - /add-comment-to-poll");
        pollDiscussionService.addComment(newCommentDTO);
    }

    @GetMapping("/get-older-poll-comments")
    List<CommentDTO> getNonRecentPollComments(@RequestParam Long pollJoinCode) {
        LOGGER.info("REQUEST - /get-older-poll-comments");
        return pollDiscussionService.getNonRecentPollComments(pollJoinCode);
    }
}
