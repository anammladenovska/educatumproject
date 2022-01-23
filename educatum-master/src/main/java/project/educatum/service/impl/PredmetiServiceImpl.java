package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Predmeti;
import project.educatum.repository.PredmetiJpa;
import project.educatum.service.PredmetiService;

import java.util.List;

@Service
public class PredmetiServiceImpl implements PredmetiService {

    private final PredmetiJpa predmetiRepository;

    public PredmetiServiceImpl(PredmetiJpa predmetiRepository) {
        this.predmetiRepository = predmetiRepository;
    }

    @Override
    public List<Predmeti> findAll() {
        return this.predmetiRepository.findAll();
    }

    @Override
    public List<Predmeti> findAllByNameLike(String imePredmet) {
        if(imePredmet != null){
            return this.predmetiRepository.findAllByImeLike(imePredmet);
        }
        else{
            return this.predmetiRepository.findAll();
        }
    }


}
