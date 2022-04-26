package project.educatum.model;

import lombok.Data;
import project.educatum.model.primarykeys.InterestID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "zainteresiran_za", schema = "project")
public class Interest {
    @EmbeddedId
    private InterestID id;

    @Column(name = "datum")
    private LocalDate date;

    public Interest(InterestID interestID, LocalDate date) {
        this.id = interestID;
        this.date = date;
    }

    public Interest() {

    }
}