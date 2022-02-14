package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predava_na", schema = "project")
public class TeacherStudentRelation {

    public TeacherStudentRelation(TeacherStudentRelationID id, Integer priceByClass, Integer numScheduledClasses) {
        this.id = id;
        this.priceByClass = priceByClass;
        this.numScheduledClasses = numScheduledClasses;
    }

    @EmbeddedId
    private TeacherStudentRelationID id;

    @Column(name = "cena_po_cas", nullable = false)
    private Integer priceByClass;

    @Column(name = "broj_casovi_po_dogovor", nullable = false)
    private Integer numScheduledClasses;

    public TeacherStudentRelation() {

    }

    public Integer getnumScheduledClasses() {
        return numScheduledClasses;
    }

    public void setnumScheduledClasses(Integer numScheduledClasses) {
        this.numScheduledClasses = numScheduledClasses;
    }

    public Integer getpriceByClass() {
        return priceByClass;
    }

    public void setpriceByClass(Integer priceByClass) {
        this.priceByClass = priceByClass;
    }

    public TeacherStudentRelationID getId() {
        return id;
    }

    public void setId(TeacherStudentRelationID id) {
        this.id = id;
    }
}