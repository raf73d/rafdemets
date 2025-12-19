package be.vdab.movies.films;

import java.math.BigDecimal;

 class FilmLogisitek {
    private final BigDecimal prijs;
    private final int voorraad;
    private final int gereserveerd;

     FilmLogisitek(BigDecimal prijs, int voorraad, int gereserveerd) {
        this.prijs = prijs;
        this.voorraad = voorraad;
        this.gereserveerd = gereserveerd;
    }

      public BigDecimal getPrijs() {
        return prijs;
    }

     public int getVoorraad() {
        return voorraad;
    }

     public int getGereserveerd() {
        return gereserveerd;
    }


}
