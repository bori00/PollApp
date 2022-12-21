package org.bori.services.poll_answering;

import org.bori.controllers.handlers.exceptions.model.ResourceNotFoundException;
import org.bori.controllers.handlers.exceptions.model.authentication.NoAccessToDataException;
import org.bori.dtos.AnswerDTO;
import org.bori.dtos.PollDTO;
import org.bori.dtos.builders.AnswerBuilder;
import org.bori.dtos.builders.PollBuilder;
import org.bori.entities.Answer;
import org.bori.entities.Poll;
import org.bori.entities.User;
import org.bori.repositories.basic.AnswersRepository;
import org.bori.repositories.basic.PollRepository;
import org.bori.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class PollAnsweringService {

    private final AnswersRepository answersRepository;

    private final AuthenticationService authenticationService;

    private final PollRepository pollRepository;

    @Autowired
    public PollAnsweringService(AnswersRepository answersRepository,
                                AuthenticationService authenticationService,
                                PollRepository pollRepository) {
        this.answersRepository = answersRepository;
        this.authenticationService = authenticationService;
        this.pollRepository = pollRepository;
    }


    public AnswerDTO findCurrentUsersAnswer(Long pollJoinCode) {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(pollJoinCode);

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + pollJoinCode);
        }

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + pollJoinCode);
        }

        return AnswerBuilder.buildAnswerDTO(optAnswer.get());
    }

    public PollDTO findPoll(Long pollJoinCode) {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(pollJoinCode);

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + pollJoinCode);
        }

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + pollJoinCode);
        }

        return PollBuilder.buildPollDTO(optPoll.get());
    }

    @Transactional(rollbackOn = Exception.class)
    public void castNewVote(Long pollJoinCode, Integer selectedOptionIndex) throws SQLException {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(pollJoinCode);

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + pollJoinCode);
        }

        if (optPoll.get().getOptions().size() <= selectedOptionIndex) {
            throw new ResourceNotFoundException("Option with index " + selectedOptionIndex + ", " +
                    "in poll with code " + pollJoinCode);
        }

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + pollJoinCode);
        }

        Answer answer = optAnswer.get();
        Poll poll = optPoll.get();

        if (answer.getSelectedOptionIndex() != null) {
            poll.getOptions().get(answer.getSelectedOptionIndex()).setNrVotes(poll.getOptions().get(answer.getSelectedOptionIndex()).getNrVotes()-1);
        }

        answer.setSelectedOptionIndex(selectedOptionIndex);
        answersRepository.save(answer);

        poll.getOptions().get(selectedOptionIndex).setNrVotes(poll.getOptions().get(selectedOptionIndex).getNrVotes()+1);
        pollRepository.save(poll);
    }

    @Transactional(rollbackOn = Exception.class)
    public void removeVote(Long pollJoinCode) throws SQLException {
        User currentUser = authenticationService.getCurrentUser("get-answer");

        Optional<Poll> optPoll = pollRepository.findPollByCode(pollJoinCode);

        if (optPoll.isEmpty()) {
            throw new ResourceNotFoundException("poll with code " + pollJoinCode);
        }

        Optional<Answer> optAnswer = answersRepository.findAnswerByUserAndPoll(currentUser,
                optPoll.get());

        if (optAnswer.isEmpty()) {
            throw new NoAccessToDataException("poll with code " + pollJoinCode);
        }

        Answer answer = optAnswer.get();
        Poll poll = optPoll.get();

        if (answer.getSelectedOptionIndex() != null) {
            poll.getOptions().get(answer.getSelectedOptionIndex()).setNrVotes(poll.getOptions().get(answer.getSelectedOptionIndex()).getNrVotes()-1);
        }

        answer.setSelectedOptionIndex(null);
        answersRepository.save(answer);

        pollRepository.save(poll);
    }
}
