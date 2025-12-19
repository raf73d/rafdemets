package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Optional;

@Entity ()
@Table(name="roles")
public class Role {
    @Id
    private long id;
    @ManyToOne(optional = false)
    @JoinColumn(name= "actorId")
    private Actor actor;
    @ManyToOne(optional = false)
    @JoinColumn(name= "movieId")
    private Movie movie;
    private String name;

    public long getId() {
        return id;
    }

    public Actor getActor() {
        return actor;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;
        return Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}