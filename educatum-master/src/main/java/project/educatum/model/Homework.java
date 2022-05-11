package project.educatum.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "domasni_zadaci", schema = "project")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domasno", nullable = false)
    private Integer id;

    @Column(name = "opis", length = 500)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cas", nullable = false)
    private Class idCas;

    public Homework(String description, Teacher idTeacher, Class idCas) {
        this.description = description;
        this.idTeacher = idTeacher;
        this.idCas = idCas;
    }

    public Homework() {
    }

}