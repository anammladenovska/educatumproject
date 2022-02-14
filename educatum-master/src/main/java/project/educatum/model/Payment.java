package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "plakjanja", schema = "project")
public class Payment {

    public Payment(){}

    public Payment(Integer iznos, Teacher idTeacher) {
        this.iznos = iznos;
        this.idTeacher = idTeacher;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plakjanja", nullable = false)
    private Integer id;

    @Column(name = "iznos", nullable = false)
    private Integer iznos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    public Teacher getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Teacher idTeacher) {
        this.idTeacher = idTeacher;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}