package be.vdab.movies.genres;

 class Genre {
   private final long id;
   private final String naam;

     Genre(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

     public long getId() {
        return id;
    }

     public String getNaam() {
        return naam;
    }
}
