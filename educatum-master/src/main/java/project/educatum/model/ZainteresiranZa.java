package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "zainteresiran_za", schema = "project")
public class ZainteresiranZa {
    @EmbeddedId
    private ZainteresiranZaId id;

    @Column(name = "datum")
    private LocalDate datum;

    public ZainteresiranZa(ZainteresiranZaId zainteresiranZaId, LocalDate datum) {
        this.id= zainteresiranZaId;
        this.datum=datum;
    }

    public ZainteresiranZa() {

    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public ZainteresiranZaId getId() {
        return id;
    }

    public void setId(ZainteresiranZaId id) {
        this.id = id;
    }
}