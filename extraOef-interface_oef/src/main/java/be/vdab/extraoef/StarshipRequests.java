package be.vdab.extraoef;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StarshipRequests {

    @GetExchange("{id}")
    Starship findById(@PathVariable int id);

}
