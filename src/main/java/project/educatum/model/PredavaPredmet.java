package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predava_predmet", schema = "project")
public class PredavaPredmet {
    @EmbeddedId
    private PredavaPredmetId id;

    @Column(name = "opis", nullable = false, length = 500)
    private String opis;

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public PredavaPredmetId getId() {
        return id;
    }

    public void setId(PredavaPredmetId id) {
        this.id = id;
    }
}