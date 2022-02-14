package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "slusanje", schema = "project")
public class Listening {

    public Listening(ListeningID id, Payment idPayment, Student studentID, Boolean plateno) {
        this.id = id;
        this.idPayment = idPayment;
        this.studentID = studentID;
        this.plateno = plateno;
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
    private Boolean plateno;

    public Listening() {

    }



    public Boolean getPlateno() {
        return plateno;
    }

    public void setPlateno(Boolean plateno) {
        this.plateno = plateno;
    }

    public Student getstudentID() {
        return studentID;
    }

    public void setstudentID(Student studentID) {
        this.studentID = studentID;
    }

    public Payment getidPayment() {
        return idPayment;
    }

    public void setidPayment(Payment idPayment) {
        this.idPayment = idPayment;
    }

    public ListeningID getId() {
        return id;
    }

    public void setId(ListeningID id) {
        this.id = id;
    }
}