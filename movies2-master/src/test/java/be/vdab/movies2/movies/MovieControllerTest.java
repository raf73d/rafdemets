package be.vdab.movies2.movies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BIG_DECIMAL;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MovieControllerTest  {
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;
    MovieControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }


     @Test
     @Sql("/movies.sql")
    void findMoviesByYearGeeftDeJuisteFilms() {
        var response = mockMvcTester
                .get()
                .uri("/movies")
                .queryParam("year", "1234");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("[0].name").isEqualTo("test1");
    }

    @Test
    void findMovieByIdMetNietBestaandeIdWerptException(){
        var response = mockMvcTester
                .get()
                .uri("/movies/{id}",Short.MAX_VALUE);
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }
    private long idVanMovieTest (String name){
        return jdbcClient.sql("select id from movies where name = ?")
                .param(name).query(Long.class).single();
    }

    @Test
    @Sql("/movies.sql")
    void findMovieByIdVindtCorrectMovie(){
        var response = mockMvcTester
                .get()
                .uri("/movies/{id}",idVanMovieTest("test1"));
        assertThat(response).hasStatus(HttpStatus.OK)
                .bodyJson()
                .extractingPath("year").isEqualTo(1234);


    }

}