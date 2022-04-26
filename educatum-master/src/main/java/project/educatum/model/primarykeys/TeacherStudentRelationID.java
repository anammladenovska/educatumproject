package project.educatum.model.primarykeys;

import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class TeacherStudentRelationID implements Serializable {
    public TeacherStudentRelationID(Integer teacherID, Integer studentID) {
        this.teacherID = teacherID;
        this.studentID = studentID;
    }

    private static final long serialVersionUID = -3956016538071516037L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer teacherID;
    @Column(name = "id_ucenik", nullable = false)
    private Integer studentID;

    public TeacherStudentRelationID() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherID, studentID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TeacherStudentRelationID entity = (TeacherStudentRelationID) o;
        return Objects.equals(this.teacherID, entity.teacherID) &&
                Objects.equals(this.studentID, entity.studentID);
    }
}