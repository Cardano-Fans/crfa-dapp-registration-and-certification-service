package de.crfa.app.crfametadataservicereloaded.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.GenerationType.AUTO;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Example {

//    @GeneratedValue(strategy = SEQUENCE, generator = "example_gen")
//    @SequenceGenerator(name = "example_gen", sequenceName = "example_SEQ", allocationSize = 1)

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private String firstname;

    private String lastname;

    private String state = "WA";

    protected Example() {}

    public Example(String firstName, String lastname, String state) {
        this.firstname = firstName;
        this.lastname = lastname;
        this.state = state;
    }

    public Example(String firstName, String lastname) {
        this.firstname = firstName;
        this.lastname = lastname;
    }

}
