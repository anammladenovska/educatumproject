package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.PredavaNa;
import project.educatum.model.PredavaNaId;
import project.educatum.model.exceptions.StudentNotFoundException;
import project.educatum.repository.PredavaNaJpa;
import project.educatum.service.PredavaNaService;

import java.util.List;

@Service
public class PredavaNaServiceImpl implements PredavaNaService {

    private final PredavaNaJpa predavaNaJpa;

    public PredavaNaServiceImpl(PredavaNaJpa predavaNaJpa) {
        this.predavaNaJpa = predavaNaJpa;
    }

    @Override
    public PredavaNa find(Integer idNastavnik, Integer idUcenik) {
        List<PredavaNa> nastavniciUcenici = predavaNaJpa.findAll();
        PredavaNa result = new PredavaNa();
        for (PredavaNa p : nastavniciUcenici) {
            PredavaNaId pId = p.getId();
            if (pId.getIdNastavnik().equals(idNastavnik) && pId.getIdUcenik().equals(idUcenik))
                result = p;
        }
        return result;
    }

    @Override
    public List<PredavaNa> findAll() {
        return predavaNaJpa.findAll();
    }
}
