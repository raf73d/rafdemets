package be.vdab.extraoef;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@RestController
@RequestMapping("mensen")
class MensController {
    private final MensRequests mensRequests;
    private final FilmRequests filmRequests;
    private final StarshipRequests starshipRequests;

    MensController(MensRequests mensRequests
            , FilmRequests filmRequests
            , StarshipRequests starshipRequests) {
        this.mensRequests = mensRequests;
        this.filmRequests = filmRequests;
        this.starshipRequests = starshipRequests;
    }
    @Cacheable(cacheNames = "EenvoudigerMensMetZijnFilmsTitels", key ="#id")
    @GetMapping("{id}")
    public EenvoudigerMensMetZijnFilmsTitels findbyId(@PathVariable int id) {
        var mens = mensRequests.findById(id);
        //var ship =  starshipRequests.findById(id);
        List<String> films = new ArrayList<>();
        List<StarshipsDTO> starships = new ArrayList<>();
        mens.result()
                .properties()
                .films()
                .forEach(uri -> films
                        .add(filmRequests
                                .findById(Integer
                                        .parseInt(uri.toString()
                                                .split("/")[5]))
                                .result().properties().title()));
        mens.result()
                .properties()
                .starships()
                .forEach(uri -> {
                    var ship = starshipRequests
                            .findById(Integer.parseInt(uri.toString().split("/")[5]))
                            .result()
                            .properties();
                    starships.add(new StarshipsDTO(ship.naam(),ship.model(),ship.fabrikant(),ship.klasse()));
                });
        return new EenvoudigerMensMetZijnFilmsTitels(mens, films, starships);
    }

    @GetMapping
    AantalRecords findAantalRecords() {
        return mensRequests.telHetaantalRecords();
    }
    private record StarshipsDTO(String naam,
                               String model,
                               String fabrikant,
                               String klasse) {

    }
    private record EenvoudigerMensMetZijnFilmsTitels(String naam,
                                                     String hoogte,
                                                     String gewicht,
                                                     LocalDate aangemaaktOp,
                                                     List<String> films,
                                                     List<StarshipsDTO> starships) {
        EenvoudigerMensMetZijnFilmsTitels(Mens mens, List<String> films, List<StarshipsDTO> starships) {
            this(mens.result().properties().naam(),
                    mens.result().properties().hoogte(),
                    mens.result().properties().gewicht(),
                    mens.result().properties().aangemaaktOp(),
                    films, starships);
        }
    }

}







