package be.vdab.extraoef;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("database")
public class DatabaseMensController {
    private final DatabaseMensService databaseMensService;

    public DatabaseMensController(DatabaseMensService databaseMensService) {
        this.databaseMensService = databaseMensService;
    }

    @PostMapping
    long create(@RequestBody  NieuweDataBaseMens nieuweDataBaseMens) {

        return databaseMensService.createDatabaseMens(nieuweDataBaseMens);

    }
}
