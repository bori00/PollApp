package org.bori.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class NewCommentDTO {
    @NotNull
    private Long pollJoinCode;

    @NotBlank(message = "The message cannot be blank.")
    @Size(min = 1, max = 100, message = "The message should have a length between 1 and " +
            "100")
    private String commentText;
}
