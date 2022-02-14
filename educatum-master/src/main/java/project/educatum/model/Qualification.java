package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "kvalifikacii", schema = "project")
public class Qualification {
    public Qualification(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kvalifikacija", nullable = false)
    private Integer id;


    @Column(name = "dokument", columnDefinition = "TEXT")
    private String dokument;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nastavnik", nullable = false)
    private Teacher idTeacher;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;


    public Qualification(String dokument, Teacher idTeacher, Admin idAdmin) {
        this.dokument = dokument;
        this.idTeacher = idTeacher;
        this.idAdmin = idAdmin;
    }

    public Admin getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Admin idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Teacher getidTeacher() {
        return idTeacher;
    }

    public void setidTeacher(Teacher idTeacher) {
        this.idTeacher = idTeacher;
    }

    public String getDokument() {
        return dokument;
    }

    public void setDokument(String dokument) {
        this.dokument = dokument;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}