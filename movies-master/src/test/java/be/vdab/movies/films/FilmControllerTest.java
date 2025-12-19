package be.vdab.movies.films;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Sql({"/genres.sql","/films.sql","/klanten.sql"})
@AutoConfigureMockMvc
class FilmControllerTest {
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;
    private final static String GENRES_TABLE = "genres";
    private final static String FILMS_TABLE = "films";
    private final static String RESERVATIES_TABLE = "reservaties";

    FilmControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    private int idVanTestFilm(String titel) {
        var sql = """
                select id 
                from films
                where titel = ?
                """;
        return jdbcClient.sql(sql).param(titel).query(Integer.class).single();
    }




    private int idVanTestGenre() {
        var sql = """
                select id
                from genres
                where naam = 'test1'
         """;
        return jdbcClient.sql(sql).query(Integer.class).single();
    }

    @Test
    void findFilmIdsByGenreIdVindtDeFilms() {
        var ids = List.of(idVanTestFilm("test1"), idVanTestFilm("test2"));
        var response = mockMvcTester.get().uri("/films")
                .queryParam("genreId", String.valueOf(idVanTestGenre()));
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()").
                isEqualTo(ids.size());

    }

    private FilmLogisitek findFilmLogisitekvanTestFilmMetTitel(String titel) {
        var sql = """
                select prijs,voorraad,gereserveerd
                from films
                where titel = ?
        """;
        return jdbcClient.sql(sql).params(titel).query(FilmLogisitek.class).single();
    }

    @Test
    void findFilmLogisitekByIdVindtDejuisteFilmOnderdelen() {
        var id = idVanTestFilm("test1");
        //prijs,voorraad,gereserveerd = 1000000
        var filmLogistiek = findFilmLogisitekvanTestFilmMetTitel("test1");
        var response = mockMvcTester.get().uri("/films/{id}", id);
        assertThat(response)
                .hasStatusOk()
                .bodyJson()
                .extractingPath("$").satisfies(prijs -> assertThat(JdbcTestUtils
                                .countRowsInTableWhere(jdbcClient, FILMS_TABLE, "prijs = " + filmLogistiek.getPrijs())).isOne(),
                        voorraad -> assertThat(JdbcTestUtils
                                .countRowsInTableWhere(jdbcClient, FILMS_TABLE, "voorraad = " + filmLogistiek.getVoorraad())).isOne(),
                        gereserveerd -> assertThat(JdbcTestUtils
                                .countRowsInTableWhere(jdbcClient, FILMS_TABLE, "gereserveerd = " + filmLogistiek.getGereserveerd())).isOne());
    }
    @Test void findFilmLogistiekByOnbestaandeFilmWerptEenException(){
        var repsonse =mockMvcTester.get().uri("/films{id}", Long.MAX_VALUE);
        assertThat(repsonse).hasStatus(HttpStatus.NOT_FOUND);

    }
    private int findIdvanTest1klant() {
        var sql = """
                select id
                from klanten
                where voornaam = 'test1'
        """;
        return jdbcClient.sql(sql).query(Integer.class).single();
    }

    @Test
    void createMetConditieOkMaaktEenRecordPerFilmInReserVatiesEnVerhoogtReservatiesMet1InFilms() throws Exception {
        var klantId1 = findIdvanTest1klant();
        var filmId1 = idVanTestFilm("test1");
        var filmId2 = idVanTestFilm("test2");
        var filmLogisitiek = findFilmLogisitekvanTestFilmMetTitel("test1");
        var gereserveerdVoor = filmLogisitiek.getGereserveerd(); //film1
        var jsonData = new ClassPathResource("klantIdFilmIdsMetPlaceHolders.json")
                .getContentAsString(StandardCharsets.UTF_8)
                .replace("{{klantId}}", String.valueOf(klantId1))
                .replace("{{filmId1}}", String.valueOf(filmId1))
                .replace("{{filmId2}}", String.valueOf(filmId2));
        assertThat(jsonData).doesNotContain("{{");
        var response = mockMvcTester.post().uri("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData);
        assertThat(response).hasStatusOk();
        var aantalReservaties = JdbcTestUtils
                .countRowsInTableWhere(jdbcClient, RESERVATIES_TABLE, "klantId= " + klantId1);
        filmLogisitiek = findFilmLogisitekvanTestFilmMetTitel("test1");
        var gereserveerdNa = filmLogisitiek.getGereserveerd(); //film1
        assertThat(aantalReservaties).isEqualTo(2); //reservaties van klant1 die film1 en film2 reserveerde
        assertThat(gereserveerdNa).isEqualTo(gereserveerdVoor + 1); //enkel check voor film1, niet voor film2
    }
     @Test
     @Sql({"/genres.sql","/film1En2NokFilm3OkOmTeReserveren.sql","/klanten.sql"})
     void createGeeftEenLijstVanIdsTerugVanFilmsDieUitverkochtZijn() throws IOException {
         var klantId1 = findIdvanTest1klant();
         var filmId1 = idVanTestFilm("test1"); //uitverkocht
         var filmId2 = idVanTestFilm("test2"); //uitverkocht
         var filmId3 = idVanTestFilm("test3");  //reserveerbaar
         var jsonData = new ClassPathResource("klantIdFilmIdsMetPlaceHolders.json")
                 .getContentAsString(StandardCharsets.UTF_8)
                 .replace("{{klantId}}", String.valueOf(klantId1))
                 .replace("{{filmId1}}", String.valueOf(filmId1))
                 .replace("{{filmId2}}", String.valueOf(filmId2))
                 .replace("{{filmId3}}", String.valueOf(filmId3));
         assertThat(jsonData).doesNotContain("{{");
         var response = mockMvcTester.post().uri("/films")
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(jsonData);
         assertThat(response)
                 .hasStatusOk()
                 .bodyJson().extractingPath("$")
                 .satisfies(ids -> {
                     var uitverkocht = (List<Integer>) ids;
                     assertThat(uitverkocht).containsExactlyInAnyOrder(filmId1,filmId2);
                     assertThat(uitverkocht).doesNotContain(filmId3);
                 });

     }
    @ParameterizedTest
    @ValueSource(strings = {"klantIdFilmIdsMetLegeFilmIds.json", "klantIdFilmIdsZonderFilmIds.json"})
    void createMetVerkeerdeFilmIdsDataMislukt(String bestandsNaam) throws Exception {
        var klantId1 = findIdvanTest1klant();
        var jsonData = new ClassPathResource(bestandsNaam)
                .getContentAsString(StandardCharsets.UTF_8)
                .replace("{{klantId}}", String.valueOf(klantId1));
        var response = mockMvcTester.post().uri("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData);
        assertThat(response).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void createMetVerkeerdeklantIdMislukt(int getal) throws Exception {
        var filmId1 = idVanTestFilm("test1");
        var filmId2 = idVanTestFilm("test2");
        var jsonData = new ClassPathResource("klantIdFilmIdsMetPlaceholders.json")
                .getContentAsString(StandardCharsets.UTF_8)
                .replace("{{klantId}}", String.valueOf(getal))
                .replace("{{filmId1}}", String.valueOf(filmId1))
                .replace("{{filmId2}}", String.valueOf(filmId2));
        var response = mockMvcTester.post().uri("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData);
        assertThat(response).hasStatus(HttpStatus.BAD_REQUEST);
    }
    }

