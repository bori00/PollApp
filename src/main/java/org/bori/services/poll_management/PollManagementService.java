package org.bori.services.poll_management;

import org.bori.controllers.handlers.exceptions.model.polls.InexistentPollException;
import org.bori.dtos.NewPollDTO;
import org.bori.dtos.PollDTO;
import org.bori.dtos.builders.PollBuilder;
import org.bori.entities.Answer;
import org.bori.entities.Poll;
import org.bori.entities.User;
import org.bori.repositories.basic.AnswersRepository;
import org.bori.repositories.basic.PollRepository;
import org.bori.repositories.custom.CustomPollRepository;
import org.bori.repositories.custom.impl.CustomPollRepositoryImpl;
import org.bori.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PollManagementService {

    private final AuthenticationService authenticationService;

    private final PollRepository pollRepository;

    private final AnswersRepository answersRepository;

    private final CustomPollRepository customPollRepository;

    @Autowired
    public PollManagementService(AuthenticationService authenticationService,
                                 PollRepository pollRepository,
                                 AnswersRepository answersRepository,
                                 CustomPollRepositoryImpl customPollRepository) {
        this.authenticationService = authenticationService;
        this.pollRepository = pollRepository;
        this.answersRepository = answersRepository;
        this.customPollRepository = customPollRepository;
    }

    @Transactional
    public void addNewPoll(NewPollDTO newPollDTO) {
        User currentUser = authenticationService.getCurrentUser("add-poll");

        // generate unique code, that is larger than all the previous ones
        Optional<Poll> highestCodePoll = pollRepository.findFirstByOrderByCodeDesc();
        Long code = highestCodePoll.isEmpty() ? 1 : highestCodePoll.get().getCode() + 1;

        Poll poll = PollBuilder.buildNewPoll(newPollDTO, currentUser, code);
        Poll savedPoll = pollRepository.save(poll);

        Answer answer = new Answer(currentUser, savedPoll, null);
        answersRepository.save(answer);
    }

    public List<PollDTO> getMyPolls() {
        User currentUser = authenticationService.getCurrentUser("add-poll");

        return customPollRepository.findPollsByJoinedUser(currentUser).stream().map(PollBuilder::buildPollDTO).collect(Collectors.toList());
    }

    public void joinPoll(Long code) {
        User currentUser = authenticationService.getCurrentUser("join-poll");

        Optional<Poll> optPoll = pollRepository.findPollByCode(code);
        if (optPoll.isEmpty()) {
            throw new InexistentPollException("join-poll", code);
        }

        Answer answer = new Answer(currentUser, optPoll.get(), null);
        answersRepository.save(answer);
    }
}
