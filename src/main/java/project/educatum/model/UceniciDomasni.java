package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ucenici_domasni", schema = "project")
public class UceniciDomasni {
    @EmbeddedId
    private UceniciDomasniId id;

    @Column(name = "dali_zavrsena")
    private Boolean daliZavrsena;

    public Boolean getDaliZavrsena() {
        return daliZavrsena;
    }

    public void setDaliZavrsena(Boolean daliZavrsena) {
        this.daliZavrsena = daliZavrsena;
    }

    public UceniciDomasniId getId() {
        return id;
    }

    public void setId(UceniciDomasniId id) {
        this.id = id;
    }
}