package org.bori.dtos.builders;

import org.bori.dtos.AnswerDTO;
import org.bori.entities.Answer;

import javax.validation.constraints.NotNull;

public class AnswerBuilder {
    public static AnswerDTO buildAnswerDTO(@NotNull Answer answer) {
        return new AnswerDTO(answer.getSelectedOptionIndex());
    }
}
