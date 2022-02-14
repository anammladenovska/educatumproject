package project.educatum.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "casovi", schema = "project")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cas", nullable = false)
    private Integer id;

    @Column(name = "vreme_pocetok")
    private LocalDateTime vremePocetok;

    @Column(name = "tema", nullable = false, length = 100)
    private String tema;

    @ManyToOne
    @JoinColumn(name = "id_nastavnik")
    private Teacher idTeacher;

    @ManyToOne
    @JoinColumn(name = "id_predmet")
    private Subject subjectID;

    public Class(LocalDateTime vremePocetok, String tema, Teacher idTeacher, Subject subjectID) {
        this.vremePocetok = vremePocetok;
        this.tema = tema;
        this.idTeacher = idTeacher;
        this.subjectID = subjectID;
    }

    public Class() {

    }

    public Subject getIdSubject() {
        return subjectID;
    }

    public void setIdSubject(Subject subjectID) {
        this.subjectID = subjectID;
    }

    public Teacher getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Teacher idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public LocalDateTime getVremePocetok() {
        return vremePocetok;
    }

    public void setVremePocetok(LocalDateTime vremePocetok) {
        this.vremePocetok = vremePocetok;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}