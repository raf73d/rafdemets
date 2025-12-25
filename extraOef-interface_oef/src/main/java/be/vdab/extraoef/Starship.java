package be.vdab.extraoef;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Starship(Result result) {
    record Result(Properties properties){
        record Properties(@JsonProperty("name")  String naam,
                          @JsonProperty("model") String model,
                          @JsonProperty("manufacturer") String fabrikant,
                          @JsonProperty("starship_class") String klasse){}
    }
}
