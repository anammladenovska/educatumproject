package project.educatum.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "plakjanja", schema = "project")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plakjanja", nullable = false)
    private Integer id;

    @Column(name = "iznos", nullable = false)
    private Integer amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    public Payment() {
    }

    public Payment(Integer amount, Teacher idTeacher) {
        this.amount = amount;
        this.idTeacher = idTeacher;
    }
}