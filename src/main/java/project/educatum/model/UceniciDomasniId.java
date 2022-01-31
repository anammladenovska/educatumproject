package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UceniciDomasniId implements Serializable {
    private static final long serialVersionUID = 8560838741150170862L;
    @Column(name = "id_ucenik", nullable = false)
    private Integer idUcenik;
    @Column(name = "id_domasno", nullable = false)
    private Integer idDomasno;

    public Integer getIdDomasno() {
        return idDomasno;
    }

    public void setIdDomasno(Integer idDomasno) {
        this.idDomasno = idDomasno;
    }

    public Integer getIdUcenik() {
        return idUcenik;
    }

    public void setIdUcenik(Integer idUcenik) {
        this.idUcenik = idUcenik;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDomasno, idUcenik);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UceniciDomasniId entity = (UceniciDomasniId) o;
        return Objects.equals(this.idDomasno, entity.idDomasno) &&
                Objects.equals(this.idUcenik, entity.idUcenik);
    }
}