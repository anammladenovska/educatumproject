package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "domasni_zadaci", schema = "project")
public class DomasniZadaci {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domasno", nullable = false)
    private Integer id;

    @Column(name = "opis", length = 500)
    private String opis;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Nastavnici idNastavnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cas", nullable = false)
    private Casovi idCas;

    public Casovi getIdCas() {
        return idCas;
    }

    public void setIdCas(Casovi idCas) {
        this.idCas = idCas;
    }

    public Nastavnici getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Nastavnici idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}