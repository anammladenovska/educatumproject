package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "plakjanja", schema = "project")
public class Plakjanja {

    public Plakjanja(){}

    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plakjanja", nullable = false)
    private Integer id;

    @Column(name = "iznos", nullable = false)
    private Integer iznos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Nastavnici idNastavnik;

    public Nastavnici getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Nastavnici idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}