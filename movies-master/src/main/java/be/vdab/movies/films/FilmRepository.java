package be.vdab.movies.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
 class FilmRepository {
    private final JdbcClient jdbcClient;

     FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<IdTitel> findFilmIdsByGenreId(long genreId) {
        var sql= """
                select id,titel
                from films
                where genreId=?
                order by titel asc
                """;
        return jdbcClient.sql(sql).param(genreId).query(IdTitel.class).list();
    }
    Optional<FilmLogisitek> findFilmLogisitiekById(long id) {
        var sql= """
                select prijs,voorraad,gereserveerd
                from films
                where id=?
        """;
        return jdbcClient.sql(sql).param(id).query(FilmLogisitek.class).optional();
    }

     List<Long> findFilmIdsBeschikbaarAndLock(List<Long> ids) {
        if (ids == null ||ids.isEmpty()) {return List.of();}
        var sql= """
                select id
                from films
                where voorraad>gereserveerd and id in (
        """
                + "?,".repeat(ids.size() - 1)
                + "?) for update";
        return jdbcClient.sql(sql).params(ids).query(Long.class).list();
    }
     void updateGereserveerd(List<Long> ids) {
         if (ids == null || ids.isEmpty()) return;
         var sql= """
                update films
                set gereserveerd = gereserveerd + 1
        where  id in (
        """
                + "?,".repeat(ids.size() - 1)
                + "?)";
          jdbcClient.sql(sql).params(ids).update();
    }
}
