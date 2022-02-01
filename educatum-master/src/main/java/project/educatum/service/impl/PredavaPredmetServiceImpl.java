package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.PredavaPredmet;
import project.educatum.model.PredavaPredmetId;
import project.educatum.repository.PredavaPredmetJpa;
import project.educatum.service.PredavaPredmetService;

@Service
public class PredavaPredmetServiceImpl implements PredavaPredmetService {

    private final PredavaPredmetJpa predavaPredmetJpa;

    public PredavaPredmetServiceImpl(PredavaPredmetJpa predavaPredmetJpa) {
        this.predavaPredmetJpa = predavaPredmetJpa;
    }

    @Override
    public void addSubject(Integer nastavnikId, Integer predmetId, String opis){

        if(nastavnikId==null){

        }
        if(predmetId==null){

        }
        PredavaPredmetId predavaPredmetId = new PredavaPredmetId(nastavnikId,predmetId);
        PredavaPredmet predavaPredmet = new PredavaPredmet(predavaPredmetId,opis);
        predavaPredmetJpa.save(predavaPredmet);

    }

}
