package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PredavaPredmetId implements Serializable {
    private static final long serialVersionUID = -3195641753836744763L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer idNastavnik;
    @Column(name = "id_predmet", nullable = false)
    private Integer idPredmet;

    public PredavaPredmetId(Integer idNastavnik, Integer idPredmet) {
        this.idNastavnik = idNastavnik;
        this.idPredmet = idPredmet;
    }

    public PredavaPredmetId() {

    }

    public Integer getIdPredmet() {
        return idPredmet;
    }

    public void setIdPredmet(Integer idPredmet) {
        this.idPredmet = idPredmet;
    }

    public Integer getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Integer idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPredmet, idNastavnik);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PredavaPredmetId entity = (PredavaPredmetId) o;
        return Objects.equals(this.idPredmet, entity.idPredmet) &&
                Objects.equals(this.idNastavnik, entity.idNastavnik);
    }
}