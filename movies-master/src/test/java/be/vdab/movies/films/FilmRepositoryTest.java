package be.vdab.movies.films;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@JdbcTest
@Import({FilmRepository.class})
@Sql({"/genres.sql","/film1En2NokFilm3OkOmTeReserveren.sql"})
class FilmRepositoryTest {
private final FilmRepository filmRepository;
private final JdbcClient jdbcClient;

    FilmRepositoryTest(FilmRepository filmRepository, JdbcClient jdbcClient) {
        this.filmRepository = filmRepository;
        this.jdbcClient = jdbcClient;
    }

    private long findfilmIdtestfilm (String titel){
        var sql= "select id from films where titel = ?";
        return jdbcClient.sql(sql).params(titel) .query(Integer.class).single();
    }
    @Test
    void findFilmIdsBeschikbaarVindtDefilmsDieMenKanReserveren(){
        var id1 = findfilmIdtestfilm("test1");//niet te reserveren
        var id2 = findfilmIdtestfilm("test2");//niet te reserveren
        var id3 = findfilmIdtestfilm("test3");//kan gereserveerd worden
        var lijstAlleFilms = List.of(id1,id2,id3);
        assertThat(filmRepository.findFilmIdsBeschikbaarAndLock(lijstAlleFilms))
                .isEqualTo(List.of(id3));
    }
}