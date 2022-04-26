package project.educatum.model.primarykeys;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class TeacherSubjectRelationID implements Serializable {
    private static final long serialVersionUID = -3195641753836744763L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer teacherID;
    @Column(name = "id_predmet", nullable = false)
    private Integer subjectID;

    public TeacherSubjectRelationID(Integer teacherID, Integer subjectID) {
        this.teacherID = teacherID;
        this.subjectID = subjectID;
    }

    public TeacherSubjectRelationID() {

    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectID, teacherID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TeacherSubjectRelationID entity = (TeacherSubjectRelationID) o;
        return Objects.equals(this.subjectID, entity.subjectID) &&
                Objects.equals(this.teacherID, entity.teacherID);
    }
}