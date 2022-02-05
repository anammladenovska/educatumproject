package project.educatum.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "predmeti", schema = "project")
public class Predmeti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_predmet", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 100)
    private String ime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admini idAdmin;

    public Predmeti(String ime, Admini idAdmin) {

        this.ime = ime;
        this.idAdmin = idAdmin;
    }

    public Predmeti() {

    }

    public Admini getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Admini idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIme() {
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