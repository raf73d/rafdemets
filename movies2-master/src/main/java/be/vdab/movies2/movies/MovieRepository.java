package be.vdab.movies2.movies;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<NameRankingDistributorName> findByYearOrderByName(short year);

    Optional<Movie> findById(long id);

   @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from Movie where id = :id")
    Optional<Movie> findAndLockById(long id);

}

