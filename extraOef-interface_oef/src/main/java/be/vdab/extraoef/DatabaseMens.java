package be.vdab.extraoef;

import jakarta.persistence.*;

@Entity
@Table(name="figuren")
public class DatabaseMens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam;
    private String hoogte;
    private String gewicht;

    public DatabaseMens(String naam, String hoogte, String gewicht) {
        this.naam = naam;
        this.hoogte = hoogte;
        this.gewicht = gewicht;
    }
   protected DatabaseMens() {}
    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getHoogte() {
        return hoogte;
    }

    public String getGewicht() {
        return gewicht;
    }
}
