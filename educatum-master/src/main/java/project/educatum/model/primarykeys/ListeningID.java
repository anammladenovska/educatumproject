package project.educatum.model.primarykeys;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class ListeningID implements Serializable {

    private static final long serialVersionUID = -8688686914310645850L;
    @Column(name = "id_cas", nullable = false)
    private Integer idClass;
    @Column(name = "id_slusanje", nullable = false)
    private Integer idListening;

    public ListeningID(Integer idClass, Integer idListening) {
        this.idClass = idClass;
        this.idListening = idListening;
    }

    public ListeningID() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(idClass, idListening);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ListeningID entity = (ListeningID) o;
        return Objects.equals(this.idClass, entity.idListening) &&
                Objects.equals(this.idListening, entity.idListening);
    }
}