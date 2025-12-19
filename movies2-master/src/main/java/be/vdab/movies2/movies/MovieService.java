package be.vdab.movies2.movies;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional (readOnly=true)
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    List<NameRankingDistributorName> findMoviesByYear(short year){
        return movieRepository.findByYearOrderByName(year);
    }

    Optional<Movie> findMovieById(long id){
        return movieRepository.findById(id);
    }
    Optional<Movie> findCommentByMovieId(long Id) {
        return movieRepository.findById(Id);
    }
     @Transactional
             void lockAndVerhoogRanking (long id){
                movieRepository.findAndLockById(id)
                        .orElseThrow(FilmNotFoundException::new)
                        .increaseRankingWithOne();
        }



}
