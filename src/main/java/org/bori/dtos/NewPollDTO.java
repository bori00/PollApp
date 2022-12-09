package org.bori.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class NewPollDTO {
    @NotBlank(message = "The title cannot be blank.")
    @Size(min = 3, max = 60, message = "The title should have a length between 3 and " +
            "60")
    private String title;

    @NotBlank(message = "The question cannot be blank.")
    @Size(min = 3, max = 300, message = "The question should have a length between 3 " +
            "and 300.")
    private String question;

    @NotEmpty(message = "There must be at least one option for this poll")
    private List<String> options;
}
