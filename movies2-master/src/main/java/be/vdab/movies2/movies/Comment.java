package be.vdab.movies2.movies;

import jakarta.persistence.Embeddable;

@Embeddable
public record Comment(String emailAddress, String comment,String moment) {
}
