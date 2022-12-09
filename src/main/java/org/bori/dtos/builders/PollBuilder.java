package org.bori.dtos.builders;

import org.bori.dtos.NewPollDTO;
import org.bori.dtos.PollDTO;
import org.bori.entities.Option;
import org.bori.entities.Poll;
import org.bori.entities.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class PollBuilder {
    public static Poll buildNewPoll(NewPollDTO newPollDTO, User user, Long code) {
        return new Poll(
                newPollDTO.getTitle(),
                newPollDTO.getQuestion(),
                code,
                user,
                newPollDTO.getOptions().stream().map(optionTitle -> new Option(optionTitle, 0)).collect(Collectors.toList()),
                new ArrayList<>()
        );
    }

    public static PollDTO buildPollDTO(Poll poll) {
        return new PollDTO(
                poll.getTitle(),
                poll.getQuestion(),
                poll.getCode(),
                poll.getUser().getUsername(),
                poll.getOptions().stream().map(option -> new PollDTO.OptionToVoteDTO(option.getTitle(), option.getNrVotes())).collect(Collectors.toList())
        );
    }
}
