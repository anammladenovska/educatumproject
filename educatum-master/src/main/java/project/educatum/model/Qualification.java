package project.educatum.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kvalifikacii", schema = "project")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kvalifikacija", nullable = false)
    private Integer id;


    @Column(name = "dokument", columnDefinition = "TEXT")
    private String document;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    public Qualification() {
    }

    public Qualification(String document, Teacher idTeacher, Admin idAdmin) {
        this.document = document;
        this.idTeacher = idTeacher;
        this.idAdmin = idAdmin;
    }

}