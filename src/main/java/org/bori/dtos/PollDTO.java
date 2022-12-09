package org.bori.dtos;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class PollDTO {
    private String title;

    private String question;

    private Long code;

    private String userName;

    private List<OptionToVoteDTO> options;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @ToString
    public static class OptionToVoteDTO {
        private String option;

        private int nrVotes;
    }
}
