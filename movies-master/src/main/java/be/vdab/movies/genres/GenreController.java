package be.vdab.movies.genres;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("genres")
 class GenreController {
    private final GenreService genreService;

     GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
     List<Genre> findAllGenres() {
        return genreService.findAllGenres();
    }
}
