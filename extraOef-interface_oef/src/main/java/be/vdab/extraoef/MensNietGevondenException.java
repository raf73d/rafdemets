package be.vdab.extraoef;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
 class MensNietGevondenException extends RuntimeException {
    public MensNietGevondenException() {
        super("Mens niet gevonden");
    }

}
