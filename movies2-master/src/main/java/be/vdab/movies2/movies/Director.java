package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="directors")
public class Director {
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private Date birthdate;
    @ManyToMany
    @JoinTable(name ="moviesdirectors"
            ,joinColumns = @JoinColumn(name ="directorId")
            ,inverseJoinColumns = @JoinColumn(name ="movieId"))
    private Set<Movie> movies;

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public Set<Movie> getMovies() {
        return Collections.unmodifiableSet(movies);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Director director)) return false;
        return Objects.equals(firstname, director.firstname) && Objects.equals(lastname, director.lastname) && Objects.equals(birthdate, director.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, birthdate);
    }
}
