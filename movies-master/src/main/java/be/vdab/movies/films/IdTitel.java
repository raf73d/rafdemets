package be.vdab.movies.films;

 class IdTitel {
    private final long id;
    private final String titel;

     IdTitel(long id, String titel) {
        this.id = id;
        this.titel = titel;
    }

     public long getId() {
        return id;
    }

      public String getTitel() {
        return titel;
    }
}
