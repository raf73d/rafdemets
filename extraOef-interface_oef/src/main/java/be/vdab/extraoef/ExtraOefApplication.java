package be.vdab.extraoef;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.service.registry.ImportHttpServices;

@SpringBootApplication
@ImportHttpServices(types = MensRequests.class, group="mensen")
@ImportHttpServices(types = FilmRequests.class, group="films")
@ImportHttpServices(types = StarshipRequests.class, group="starships")
@EnableCaching
public class ExtraOefApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtraOefApplication.class, args);
    }

}
