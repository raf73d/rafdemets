package be.vdab.movies.films;

import be.vdab.movies.reservaties.ReservatieRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
 class FilmService {
    private final FilmRepository filmRepository;
    private final ReservatieRepository reservatieRepository;
     FilmService(FilmRepository filmRepository, ReservatieRepository reservatieRepository) {
        this.filmRepository = filmRepository;
        this.reservatieRepository = reservatieRepository;
    }
    List<IdTitel> findFilmIdsByGenreId(long genreId) {
        return filmRepository.findFilmIdsByGenreId(genreId);
    }
    Optional<FilmLogisitek> findFilmLogisitiekById(long id) {
        return filmRepository.findFilmLogisitiekById(id);
    }
    @Transactional
     List<Long> createReservaties (long klantId, List<Long>filmIds) {
        var filmIdsOk = filmRepository.findFilmIdsBeschikbaarAndLock(filmIds);
        filmRepository.updateGereserveerd(filmIdsOk);
        reservatieRepository.createReservaties(klantId, filmIdsOk);
        return filmIds.stream().filter(id ->!filmIdsOk.contains(id)).toList();
        //geef een lijst van id's terug van films die niet te reserveren zijn.
    }
}
