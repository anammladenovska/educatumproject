package project.educatum.model.relations;

import lombok.Data;
import project.educatum.model.primarykeys.TeacherSubjectRelationID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "predava_predmet", schema = "project")
public class TeacherSubjectRelation {

    @EmbeddedId
    private TeacherSubjectRelationID id;

    @Column(name = "opis", nullable = false, length = 500)
    private String description;

    public TeacherSubjectRelation(TeacherSubjectRelationID id, String description) {
        this.id = id;
        this.description = description;
    }

    public TeacherSubjectRelation() {

    }

}