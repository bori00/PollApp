package org.bori.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class NewVoteDTO {

    @NotNull
    public Long pollJoinCode;

    @NotNull
    public Integer selectedOptionIndex;
}
