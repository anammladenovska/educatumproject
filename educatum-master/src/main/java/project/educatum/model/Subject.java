package project.educatum.model;

import javax.persistence.*;

@Entity
@Table(name = "predmeti", schema = "project")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_predmet", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 100)
    private String ime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    public Subject(String ime, Admin idAdmin) {

        this.ime = ime;
        this.idAdmin = idAdmin;
    }

    public Subject() {

    }

    @Override
    public String toString() {
        return ime;
    }

    public Admin getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Admin idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getName() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}