package be.vdab.extraoef;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface FilmRequests {
    @GetExchange("{id}")
    Film findById(@PathVariable int id);
}
