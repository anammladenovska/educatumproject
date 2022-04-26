package project.educatum.model.relations;

import lombok.Data;
import project.educatum.model.primarykeys.TeacherStudentRelationID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "predava_na", schema = "project")
public class TeacherStudentRelation {

    @EmbeddedId
    private TeacherStudentRelationID id;

    @Column(name = "cena_po_cas", nullable = false)
    private Integer priceByClass;

    @Column(name = "broj_casovi_po_dogovor", nullable = false)
    private Integer numScheduledClasses;

    public TeacherStudentRelation() {
    }

    public TeacherStudentRelation(TeacherStudentRelationID id, Integer priceByClass, Integer numScheduledClasses) {
        this.id = id;
        this.priceByClass = priceByClass;
        this.numScheduledClasses = numScheduledClasses;
    }
}