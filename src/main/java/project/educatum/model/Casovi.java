package project.educatum.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "casovi", schema = "project")
public class Casovi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cas", nullable = false)
    private Integer id;

    @Column(name = "vreme_pocetok")
    private Instant vremePocetok;

    @Column(name = "tema", nullable = false, length = 100)
    private String tema;

    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    private Nastavnici idNastavnik;

    @ManyToOne
    @JoinColumn(name = "id_predmet")
    private Predmeti idPredmet;

    public Predmeti getIdPredmet() {
        return idPredmet;
    }

    public void setIdPredmet(Predmeti idPredmet) {
        this.idPredmet = idPredmet;
    }

    public Nastavnici getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Nastavnici idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Instant getVremePocetok() {
        return vremePocetok;
    }

    public void setVremePocetok(Instant vremePocetok) {
        this.vremePocetok = vremePocetok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}