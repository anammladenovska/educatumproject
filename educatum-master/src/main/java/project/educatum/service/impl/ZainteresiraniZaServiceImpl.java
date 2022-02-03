package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.ZainteresiranZa;
import project.educatum.model.ZainteresiranZaId;
import project.educatum.repository.ZainteresiranZaJpa;
import project.educatum.service.ZainteresiraniZaService;

import java.time.LocalDate;
import java.util.Date;

@Service
public class ZainteresiraniZaServiceImpl implements ZainteresiraniZaService {

    private final ZainteresiranZaJpa zainteresiranZaJpa;

    public ZainteresiraniZaServiceImpl(ZainteresiranZaJpa zainteresiranZaJpa) {
        this.zainteresiranZaJpa = zainteresiranZaJpa;
    }

    @Override
    public void addSubjectStudent(Integer predmetId, Integer ucenikId, LocalDate datum) {

        if(predmetId==null){

        }
        if(ucenikId==null){

        }

        ZainteresiranZaId zainteresiranZaId = new ZainteresiranZaId(predmetId,ucenikId);
        ZainteresiranZa zainteresiranZa = new ZainteresiranZa(zainteresiranZaId,datum);
        zainteresiranZaJpa.save(zainteresiranZa);
    }
}
