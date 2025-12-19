package be.vdab.extraoef;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.net.URI;
import java.time.LocalDate;
import java.util.List;

//name en height moeten strings zijn daar er soms unknowns zijn
public record Mens(Result result) {

    record Result(Properties properties, int uid){
        record Properties(@JsonProperty("created") LocalDate aangemaaktOp
                , @JsonProperty("name") String naam
                , @JsonProperty("height") String hoogte
                , @JsonProperty("mass") String gewicht,
                          List<URI> films){}

    }
}

