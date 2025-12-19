package be.vdab.movies.films;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
 class FilmNietGevondenException extends RuntimeException {
	 FilmNietGevondenException(long id ) {
		super("film niet gevonden met id: " + id);
	}
}
