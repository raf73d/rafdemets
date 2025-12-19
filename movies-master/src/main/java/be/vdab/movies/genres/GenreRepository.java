package be.vdab.movies.genres;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
 class GenreRepository {
    private final JdbcClient jdbcClient;
     GenreRepository(JdbcClient jdbcClient, JdbcClient jdbcClient1) {
        this.jdbcClient = jdbcClient1;
    }

    List<Genre> findAllGenres() {
        var sql= """
                select id,naam
                from genres
                order by naam
                """;
        return jdbcClient.sql(sql).query(Genre.class).list();
    }
}
