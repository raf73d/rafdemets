package be.vdab.movies.reservaties;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
 public class ReservatieRepository {
    private final JdbcClient jdbcClient;

     ReservatieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

     public void createReservaties (long klantId, List<Long> ids) {
         if (ids == null || ids.isEmpty()) {
             return;
         }


         var sql= """
            insert into reservaties(klantId,filmId,reservatie)
            values
            """
            + "(?,?,?),".repeat(ids.size() - 1)
            + "(?,?,?)";
    var parameters = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    for (Long filmId : ids) {
        parameters.add(klantId);
        parameters.add(filmId);
        parameters.add(now);
    }
    jdbcClient.sql(sql).params(parameters.toArray()).update();
    }
}
