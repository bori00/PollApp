package org.bori.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class CommentDTO {
    private String text;

    private String senderName;

    private LocalDateTime localDateTime;
}
