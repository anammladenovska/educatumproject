package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeacherSubjectRelationID implements Serializable {
    private static final long serialVersionUID = -3195641753836744763L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer idTeacher;
    @Column(name = "id_predmet", nullable = false)
    private Integer subjectID;

    public TeacherSubjectRelationID(Integer idTeacher, Integer subjectID) {
        this.idTeacher = idTeacher;
        this.subjectID = subjectID;
    }

    public TeacherSubjectRelationID() {

    }

    public Integer getsubjectID() {
        return subjectID;
    }

    public void setsubjectID(Integer subjectID) {
        this.subjectID = subjectID;
    }

    public Integer getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Integer idTeacher) {
        this.idTeacher = idTeacher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectID, idTeacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TeacherSubjectRelationID entity = (TeacherSubjectRelationID) o;
        return Objects.equals(this.subjectID, entity.subjectID) &&
                Objects.equals(this.idTeacher, entity.idTeacher);
    }
}