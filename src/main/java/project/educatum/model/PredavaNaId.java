package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PredavaNaId implements Serializable {
    private static final long serialVersionUID = -3956016538071516037L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer idNastavnik;
    @Column(name = "id_ucenik", nullable = false)
    private Integer idUcenik;

    public Integer getIdUcenik() {
        return idUcenik;
    }

    public void setIdUcenik(Integer idUcenik) {
        this.idUcenik = idUcenik;
    }

    public Integer getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Integer idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNastavnik, idUcenik);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PredavaNaId entity = (PredavaNaId) o;
        return Objects.equals(this.idNastavnik, entity.idNastavnik) &&
                Objects.equals(this.idUcenik, entity.idUcenik);
    }
}