package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ucenici_domasni", schema = "project")
public class StudentHomeworkRelation {
    @EmbeddedId
    private StudentHomeworkRelationID id;

    @Column(name = "dali_zavrsena")
    private Boolean daliZavrsena;

    public Boolean getDaliZavrsena() {
        return daliZavrsena;
    }

    public void setDaliZavrsena(Boolean daliZavrsena) {
        this.daliZavrsena = daliZavrsena;
    }

    public StudentHomeworkRelationID getId() {
        return id;
    }

    public void setId(StudentHomeworkRelationID id) {
        this.id = id;
    }
}