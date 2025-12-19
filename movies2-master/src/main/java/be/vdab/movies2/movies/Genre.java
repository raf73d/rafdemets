package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name= "genres")
public class Genre {
    @Id
    private long id;
    private String name;
    @ManyToMany
    @JoinTable(name ="moviesgenres"
            ,joinColumns = @JoinColumn(name ="genreId")
    ,inverseJoinColumns = @JoinColumn(name ="movieId"))
    private Set<Movie> movies;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Movie> getMovies() {
        return Collections.unmodifiableSet(movies);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Genre genre)) return false;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
