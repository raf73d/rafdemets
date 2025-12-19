package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private long id;
    private String name;
    private short year;
    private BigDecimal ranking;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "distributorId")
    private Distributor distributor;
    @ManyToMany(mappedBy = "movies")
    @OrderBy("name")
    private Set<Genre> genres;
    @ManyToMany(mappedBy = "movies")
    @OrderBy("firstname")
    private Set<Director> directors;

    @OneToMany (mappedBy = "movie")
    @OrderBy("name")
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @ElementCollection
    @CollectionTable(name = "comments"
    ,joinColumns = @JoinColumn(name= "movieId"))
    @OrderBy("moment")
    private Set<Comment> comments;

    public Movie(String name, short year,BigDecimal ranking,Distributor distributor) {
        this.name = name;
        this.year = year;
        this.ranking = ranking;
        this.distributor = distributor;
        genres = new LinkedHashSet<Genre>();
        directors = new LinkedHashSet<Director>();
        comments = new LinkedHashSet<Comment>();
    }

    protected Movie() {
    }

    public long getId() {
        return id;
    }

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public String getName() {
        return name;
    }

    public short getYear() {
        return year;
    }

    public BigDecimal getRanking() {
        return ranking;
    }

    public Distributor getDistributor() {
        return distributor;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Movie movie)) return false;
        return Objects.equals(name, movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public Set<Director> getDirectors() {
        return Collections.unmodifiableSet(directors);
    }

    public Set<Comment> getComments() {
        return Collections.unmodifiableSet(comments);
    }
    void increaseRankingWithOne() {
        this.ranking = this.ranking.add(BigDecimal.ONE);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
