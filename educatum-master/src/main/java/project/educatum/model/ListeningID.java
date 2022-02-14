package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ListeningID implements Serializable {

    public ListeningID(Integer idCas, Integer idSlusanje) {
        this.idCas = idCas;
        this.idSlusanje = idSlusanje;
    }

    private static final long serialVersionUID = -8688686914310645850L;
    @Column(name = "id_cas", nullable = false)
    private Integer idCas;
    @Column(name = "id_slusanje", nullable = false)
    private Integer idSlusanje;

    public ListeningID() {

    }

    public Integer getIdSlusanje() {
        return idSlusanje;
    }

    public void setIdSlusanje(Integer idSlusanje) {
        this.idSlusanje = idSlusanje;
    }

    public Integer getIdCas() {
        return idCas;
    }

    public void setIdCas(Integer idCas) {
        this.idCas = idCas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCas, idSlusanje);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListeningID entity = (ListeningID) o;
        return Objects.equals(this.idCas, entity.idCas) &&
                Objects.equals(this.idSlusanje, entity.idSlusanje);
    }
}