package project.educatum.model;

import lombok.Data;
import project.educatum.model.primarykeys.ListeningID;

import javax.persistence.*;

@Data
@Entity
@Table(name = "slusanje", schema = "project")
public class Listening {

    public Listening(ListeningID id, Payment idPayment, Student studentID, Boolean paid) {
        this.id = id;
        this.idPayment = idPayment;
        this.studentID = studentID;
        this.paid = paid;
    }

    @EmbeddedId
    private ListeningID id;

    @ManyToOne
    @JoinColumn(name = "id_plakjanja")
    private Payment idPayment;

    @ManyToOne
    @JoinColumn(name = "id_ucenik")
    private Student studentID;

    @Column(name = "plateno")
    private Boolean paid;

    public Listening() {
    }
}