package project.educatum.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "predmeti", schema = "project")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_predmet", nullable = false)
    private Integer id;

    @Column(name = "ime", nullable = false, length = 100)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_admin", nullable = false)
    private Admin idAdmin;

    public Subject(String name, Admin idAdmin) {
        this.name = name;
        this.idAdmin = idAdmin;
    }

    public Subject() {
    }

    @Override
    public String toString() {
        return name;
    }

}