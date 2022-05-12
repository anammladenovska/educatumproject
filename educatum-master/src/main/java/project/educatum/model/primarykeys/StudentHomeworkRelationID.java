package project.educatum.model.primarykeys;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class StudentHomeworkRelationID implements Serializable {

    private static final long serialVersionUID = 8560838741150170862L;
    @Column(name = "id_ucenik", nullable = false)
    private Integer studentID;
    @Column(name = "id_domasno", nullable = false)
    private Integer homeworkID;

    @Override
    public int hashCode() {
        return Objects.hash(homeworkID, studentID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentHomeworkRelationID entity = (StudentHomeworkRelationID) o;
        return Objects.equals(this.homeworkID, entity.homeworkID) &&
                Objects.equals(this.studentID, entity.studentID);
    }
}