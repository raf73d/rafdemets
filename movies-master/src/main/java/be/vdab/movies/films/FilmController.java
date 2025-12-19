package be.vdab.movies.films;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("films")
 class FilmController {
    private final FilmService filmService;

     FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(params = "genreId")
     List<IdTitel> findFilmIdsByGenreId(long genreId) {
        return filmService.findFilmIdsByGenreId(genreId);
    }

    @GetMapping("{id}")
     FilmLogisitek findFilmLogistiekById(@PathVariable long id) {
        return filmService.findFilmLogisitiekById(id)
                .orElseThrow(() -> new FilmNietGevondenException(id));
    }

 private record KlantIdFilmIds (@Min(1)long klantId, @NotEmpty List<Long>filmIds){}

    @PostMapping
    public List<Long> create(@RequestBody @Valid KlantIdFilmIds klantIdFilmIds) {
        var ids = filmService.createReservaties(klantIdFilmIds.klantId, klantIdFilmIds.filmIds);
        return ids;
    }

}