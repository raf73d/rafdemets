package be.vdab.movies2.movies;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name="distributors")
public class Distributor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;
    private String name;





    public Distributor(String name) {
        this.name = name;

    }
    protected Distributor() {}
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
