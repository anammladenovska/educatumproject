package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "domasni_zadaci", schema = "project")
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_domasno", nullable = false)
    private Integer id;

    @Column(name = "opis", length = 500)
    private String opis;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cas", nullable = false)
    private Class idCas;

    public Class getIdCas() {
        return idCas;
    }

    public void setIdCas(Class idCas) {
        this.idCas = idCas;
    }

    public Teacher getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Teacher idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getDescription() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}