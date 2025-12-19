package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;


@Entity
@Table(name="actors")

public class Actor {
    @Id
    private long id;
    private String firstname;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Gender gender;


    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

}
