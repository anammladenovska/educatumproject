package project.educatum.model;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TeacherStudentRelationID implements Serializable {
    public TeacherStudentRelationID(Integer idTeacher, Integer studentID) {
        this.idTeacher = idTeacher;
        this.studentID = studentID;
    }

    private static final long serialVersionUID = -3956016538071516037L;
    @Column(name = "id_nastavnik", nullable = false)
    private Integer idTeacher;
    @Column(name = "id_ucenik", nullable = false)
    private Integer studentID;

    public TeacherStudentRelationID() {

    }


    public Integer getstudentID() {
        return studentID;
    }

    public void setstudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Integer idTeacher) {
        this.idTeacher = idTeacher;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTeacher, studentID);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TeacherStudentRelationID entity = (TeacherStudentRelationID) o;
        return Objects.equals(this.idTeacher, entity.idTeacher) &&
                Objects.equals(this.studentID, entity.studentID);
    }
}