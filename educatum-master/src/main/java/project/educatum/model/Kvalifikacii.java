package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "kvalifikacii", schema = "project")
public class Kvalifikacii {



    public Kvalifikacii(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kvalifikacija", nullable = false)
    private Integer id;


    @Column(name = "dokument", columnDefinition = "TEXT")
    private String dokument;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Nastavnici idNastavnik;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admini idAdmin;


    public Kvalifikacii(String dokument, Nastavnici idNastavnik, Admini idAdmin) {
        this.dokument = dokument;
        this.idNastavnik = idNastavnik;
        this.idAdmin = idAdmin;
    }

    public Admini getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Admini idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Nastavnici getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Nastavnici idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public String getDokument() {
        return dokument;
    }

    public void setDokument(String dokument) {
        this.dokument = dokument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}