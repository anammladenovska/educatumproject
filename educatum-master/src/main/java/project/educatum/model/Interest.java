package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "zainteresiran_za", schema = "project")
public class Interest {
    @EmbeddedId
    private InterestID id;

    @Column(name = "datum")
    private LocalDate datum;

    public Interest(InterestID interestID, LocalDate datum) {
        this.id= interestID;
        this.datum=datum;
    }

    public Interest() {

    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public InterestID getId() {
        return id;
    }

    public void setId(InterestID id) {
        this.id = id;
    }
}