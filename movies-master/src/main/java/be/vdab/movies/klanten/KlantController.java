package be.vdab.movies.klanten;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("klanten")
 class KlantController {
    private final KlantService klantService;

     KlantController(KlantService klantService) {
        this.klantService = klantService;
    }
    @GetMapping(params="woord")
     List<Klant> findKlantenByWoord(String woord){
        return klantService.findKlantenByWord(woord);
    }
}
