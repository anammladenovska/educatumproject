package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ZainteresiranZaId implements Serializable {
    private static final long serialVersionUID = 1443493244068543984L;
    @Column(name = "id_ucenik", nullable = false)
    private Integer idUcenik;
    @Column(name = "id_predmet", nullable = false)
    private Integer idPredmet;

    public ZainteresiranZaId(Integer predmetId, Integer ucenikId) {
        this.idPredmet=predmetId;
        this.idUcenik=ucenikId;
    }

    public ZainteresiranZaId() {

    }

    public Integer getIdPredmet() {
        return idPredmet;
    }

    public void setIdPredmet(Integer idPredmet) {
        this.idPredmet = idPredmet;
    }

    public Integer getIdUcenik() {
        return idUcenik;
    }

    public void setIdUcenik(Integer idUcenik) {
        this.idUcenik = idUcenik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPredmet, idUcenik);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ZainteresiranZaId entity = (ZainteresiranZaId) o;
        return Objects.equals(this.idPredmet, entity.idPredmet) &&
                Objects.equals(this.idUcenik, entity.idUcenik);
    }
}