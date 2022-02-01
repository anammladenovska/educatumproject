package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Predmeti;
import project.educatum.repository.PredmetiJpa;
import project.educatum.service.PredmetiService;

import java.util.List;
import java.util.Optional;

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
    public List<Predmeti> findAllByNameLike(String ime) {
        if(ime != null){
            return this.predmetiRepository.findAllByImeContainingIgnoreCase(ime);
        }
        else{
            return this.predmetiRepository.findAll();
        }
    }

    @Override
    public Optional<Predmeti> findById(Integer id) {
        return predmetiRepository.findById(id);
    }


}
