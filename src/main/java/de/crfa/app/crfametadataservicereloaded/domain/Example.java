package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Example {

//    @GeneratedValue(strategy = AUTO)

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "example_gen")
    @SequenceGenerator(name = "example_gen", sequenceName = "example_SEQ", allocationSize = 1)
    private Long id;

    private String firstname;

    private String lastname;

    private char state = 'W';

    protected Example() {}

    public Example(String firstName, String lastname, char state) {
        this.firstname = firstName;
        this.lastname = lastname;
        this.state = state;
    }

    public Example(String firstName, String lastname) {
        this.firstname = firstName;
        this.lastname = lastname;
    }

}
