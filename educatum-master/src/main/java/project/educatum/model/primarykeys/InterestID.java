package project.educatum.model.primarykeys;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class InterestID implements Serializable {
    private static final long serialVersionUID = 1443493244068543984L;
    @Column(name = "id_ucenik", nullable = false)
    private Integer studentID;
    @Column(name = "id_predmet", nullable = false)
    private Integer subjectID;

    public InterestID(Integer subjectID, Integer ucenikId) {
        this.subjectID = subjectID;
        this.studentID = ucenikId;
    }

    public InterestID() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectID, studentID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InterestID entity = (InterestID) o;
        return Objects.equals(this.subjectID, entity.subjectID) &&
                Objects.equals(this.studentID, entity.studentID);
    }
}