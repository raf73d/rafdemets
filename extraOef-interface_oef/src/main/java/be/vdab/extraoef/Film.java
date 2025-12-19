package be.vdab.extraoef;

import java.net.URI;

public record Film(Result result) {
    record Result(Properties properties){
        record Properties(String title){}
    }



}
