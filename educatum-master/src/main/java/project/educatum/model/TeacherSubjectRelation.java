package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predava_predmet", schema = "project")
public class TeacherSubjectRelation {
    @EmbeddedId
    private TeacherSubjectRelationID id;

    @Column(name = "opis", nullable = false, length = 500)
    private String opis;

    public TeacherSubjectRelation(TeacherSubjectRelationID id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public TeacherSubjectRelation() {

    }

    public String getDescription() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public TeacherSubjectRelationID getId() {
        return id;
    }

    public void setId(TeacherSubjectRelationID id) {
        this.id = id;
    }
}