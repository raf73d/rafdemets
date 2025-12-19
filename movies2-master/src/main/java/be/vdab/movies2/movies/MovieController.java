package be.vdab.movies2.movies;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(params = "year")
    List<NameRankingDistributorName> findMoviesByYear (short year){
        return movieService.findMoviesByYear(year);
    }

    @GetMapping("{id}")
    MovieDTO findMovieById (@PathVariable int id){
        return movieService.findMovieById(id)
                .map(movie -> new MovieDTO(movie))
                .orElseThrow(FilmNotFoundException::new);
    }
    private record FirstLastName (String firstName, String lastName){
        FirstLastName (Director director){
            this(director.getFirstname(),director.getLastname() );
        }
    }
    private record RolFirstLastGender (String rolName,String firstName,String lastName,Gender gender){
        RolFirstLastGender(Role role){
            this(role.getName(),role.getActor().getFirstname(),role.getActor().getLastName(),role.getActor().getGender());
        }
    }

    private record MovieDTO(String name, Short year, BigDecimal ranking, Set<String> genreNames, Set<FirstLastName> directorNames, Set<RolFirstLastGender> roleDetails) {
         MovieDTO(Movie movie){
             this(movie.getName()
                     , movie.getYear()
                     , movie.getRanking()
                     , movie.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
                     , movie.getDirectors().stream().map(director -> new FirstLastName(director)).collect(Collectors.toSet())
                     , movie.getRoles().stream().map(role -> new RolFirstLastGender(role)).collect(Collectors.toSet()));
         }
    }

@GetMapping("{id}/comment")
Set<Comment> findCommentById (@PathVariable long id) {
    return movieService.findMovieById(id)
            .orElseThrow(FilmNotFoundException::new)
            .getComments();
}
@PostMapping("{id}/increaseRanking")

        void verhoogRankingVanFilm(@PathVariable long id) {

           movieService.lockAndVerhoogRanking(id);}
    }




 /*   private record MovieMetCommentaren(Set<String> emailAddress, Set<String> comment, Set<String> moment){
        MovieMetCommentaren(Movie movie){
            this(movie.g)
        }*/


