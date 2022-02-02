package project.educatum.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "predava_na", schema = "project")
public class PredavaNa {

    public PredavaNa(PredavaNaId id, Integer cenaPoCas, Integer brojCasoviPoDogovor) {
        this.id = id;
        this.cenaPoCas = cenaPoCas;
        this.brojCasoviPoDogovor = brojCasoviPoDogovor;
    }

    @EmbeddedId
    private PredavaNaId id;

    @Column(name = "cena_po_cas", nullable = false)
    private Integer cenaPoCas;

    @Column(name = "broj_casovi_po_dogovor", nullable = false)
    private Integer brojCasoviPoDogovor;

    public PredavaNa() {

    }

    public Integer getBrojCasoviPoDogovor() {
        return brojCasoviPoDogovor;
    }

    public void setBrojCasoviPoDogovor(Integer brojCasoviPoDogovor) {
        this.brojCasoviPoDogovor = brojCasoviPoDogovor;
    }

    public Integer getCenaPoCas() {
        return cenaPoCas;
    }

    public void setCenaPoCas(Integer cenaPoCas) {
        this.cenaPoCas = cenaPoCas;
    }

    public PredavaNaId getId() {
        return id;
    }

    public void setId(PredavaNaId id) {
        this.id = id;
    }
}