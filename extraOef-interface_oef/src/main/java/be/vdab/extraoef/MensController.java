package be.vdab.extraoef;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;


@RestController
@RequestMapping("mensen")
class MensController {
    private final MensRequests mensRequests;
    private final FilmRequests filmRequests;

    MensController(@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") MensRequests mensRequests
            ,@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") FilmRequests filmRequests) {//
        this.mensRequests = mensRequests;
        this.filmRequests = filmRequests;
    }
    @GetMapping("{id}")
    EenvoudigerMensMetZijnFilmsTitels findbyId(@PathVariable int id) {
       var mens =  mensRequests.findById(id);

        List<String> films = new ArrayList<>();
        mens.result()
               .properties()
               .films()
               .forEach(uri-> films.add(filmRequests.findById(Integer.parseInt(uri.toString().split("/")[5])).result().properties().title()));
      return new EenvoudigerMensMetZijnFilmsTitels(mens,films);
    }
    @GetMapping
    AantalRecords findAantalRecords() {
        return mensRequests.telHetaantalRecords();
    }
    private record EenvoudigerMensMetZijnFilmsTitels(String naam,
                                                     String hoogte,
                                                     String gewicht,
                                                     LocalDate aangemaaktOp,
                                                     List<String> films) {
        EenvoudigerMensMetZijnFilmsTitels(Mens mens, List<String> films)
        {
            this(mens.result().properties().naam(),
                    mens.result().properties().hoogte(),
                    mens.result().properties().gewicht(),
                    mens.result().properties().aangemaaktOp(),
                    films);
        }
    }

}







