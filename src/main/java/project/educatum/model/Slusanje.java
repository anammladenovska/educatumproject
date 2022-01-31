package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "slusanje", schema = "project")
public class Slusanje {
    @EmbeddedId
    private SlusanjeId id;

    @ManyToOne
    @JoinColumn(name = "id_plakjanja")
    private Plakjanja idPlakjanja;

    @ManyToOne
    @JoinColumn(name = "id_ucenik")
    private Ucenici idUcenik;

    @Column(name = "plateno")
    private Boolean plateno;

    public Boolean getPlateno() {
        return plateno;
    }

    public void setPlateno(Boolean plateno) {
        this.plateno = plateno;
    }

    public Ucenici getIdUcenik() {
        return idUcenik;
    }

    public void setIdUcenik(Ucenici idUcenik) {
        this.idUcenik = idUcenik;
    }

    public Plakjanja getIdPlakjanja() {
        return idPlakjanja;
    }

    public void setIdPlakjanja(Plakjanja idPlakjanja) {
        this.idPlakjanja = idPlakjanja;
    }

    public SlusanjeId getId() {
        return id;
    }

    public void setId(SlusanjeId id) {
        this.id = id;
    }
}