package org.bori.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.persistence.OneToMany;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
//@Embeddable
public class Option {

    @Id
    private String Id;

    private String title;

    private int nrVotes;

    public Option(String title, int nrVotes) {
        this.title = title;
        this.nrVotes = nrVotes;
    }
}
