package org.bori.services.poll_discussion;

import org.bori.controllers.handlers.exceptions.model.ResourceNotFoundException;
import org.bori.controllers.handlers.exceptions.model.authentication.NoAccessToDataException;
import org.bori.dtos.CommentDTO;
import org.bori.dtos.NewCommentDTO;
import org.bori.dtos.builders.CommentBuilder;
import org.bori.entities.Answer;
import org.bori.entities.Comment;
import org.bori.entities.Poll;
import org.bori.entities.User;
import org.bori.repositories.basic.AnswersRepository;
import org.bori.repositories.basic.CommentRepository;
import org.bori.repositories.basic.PollRepository;
import org.bori.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollDiscussionService {

    private final CommentRepository commentRepository;

    private final AuthenticationService authenticationService;

    private final PollRepository pollRepository;

    private final AnswersRepository answersRepository;

    @Autowired
    public PollDiscussionService(CommentRepository commentRepository,
                                 AuthenticationService authenticationService,
                                 PollRepository pollRepository,
                                 AnswersRepository answersRepository) {
        this.commentRepository = commentRepository;
        this.authenticationService = authenticationService;
        this.pollRepository = pollRepository;
        this.answersRepository = answersRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void addComment(NewCommentDTO newCommentDTO) {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(newCommentDTO.getPollJoinCode());

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + newCommentDTO.getPollJoinCode());
        }

        Poll poll = optPoll.get();

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + newCommentDTO.getPollJoinCode());
        }

        while (poll.getRecentComments().size() >= Poll.RECENT_COMMENTS_NR) {
            Comment oldRecentComment =
                    poll.getRecentComments().get(0);

            Comment nonRecentComment = new Comment(oldRecentComment.getUser(),
                    oldRecentComment.getPoll(), oldRecentComment.getDateTime(),
                    oldRecentComment.getMessage());

            commentRepository.save(nonRecentComment);
            poll.getRecentComments().remove(0);
        }

        poll.getRecentComments().add(CommentBuilder.buildComment(newCommentDTO, currentUser,
                poll));

        pollRepository.save(poll);
    }

    public List<CommentDTO> getNonRecentPollComments(Long pollJoinCode) {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(pollJoinCode);

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + pollJoinCode);
        }

        Poll poll = optPoll.get();

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + pollJoinCode);
        }

        return commentRepository.findCommentsByPollOrderByDateTimeDesc(poll).stream().map(CommentBuilder::buildCommentDTO).collect(Collectors.toList());
    }
}
