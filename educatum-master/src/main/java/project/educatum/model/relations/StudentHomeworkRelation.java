package project.educatum.model.relations;

import lombok.Data;
import project.educatum.model.primarykeys.StudentHomeworkRelationID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ucenici_domasni", schema = "project")
public class StudentHomeworkRelation {
    @EmbeddedId
    private StudentHomeworkRelationID id;

    @Column(name = "dali_zavrsena")
    private Boolean isDone;

    public StudentHomeworkRelation() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }
}