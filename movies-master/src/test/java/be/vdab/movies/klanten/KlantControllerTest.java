package be.vdab.movies.klanten;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Sql("/klanten.sql")
@AutoConfigureMockMvc
class KlantControllerTest {
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;
    private final static String KLANTEN_TABLE = "klanten";
    KlantControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }


    @Test
    void findKlantenByWoordVindtDeJuisteKlanten() {
        var response = mockMvcTester.get().uri("/klanten")
                .queryParam("woord", "test");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils
                        .countRowsInTableWhere(jdbcClient, KLANTEN_TABLE,"familienaam like '%test%'"));
    }

    @Test
    void geenKlantenGevondenResulteertInEenLegeLijst(){
        var response = mockMvcTester.get().uri("/klanten")
                .queryParam("woord", "xxxxxxxxxxxxxxxxxxxxxx");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()").isEqualTo(0);

    }

}