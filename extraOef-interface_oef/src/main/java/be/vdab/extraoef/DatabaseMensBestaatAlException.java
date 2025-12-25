package be.vdab.extraoef;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DatabaseMensBestaatAlException extends RuntimeException {
    public DatabaseMensBestaatAlException() {
        super("Zit reeds in de database!");
    }
}
