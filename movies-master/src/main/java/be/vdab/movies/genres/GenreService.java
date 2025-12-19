package be.vdab.movies.genres;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
 class GenreService {
    private final GenreRepository genreRepository;

     GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    List<Genre> findAllGenres() {
        return genreRepository.findAllGenres();
    }
}
