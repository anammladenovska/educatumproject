package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Predmeti;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.PredmetiJpa;
import project.educatum.service.PredmetiService;

import java.util.List;
import java.util.Optional;

@Service
public class PredmetiServiceImpl implements PredmetiService {

    private final PredmetiJpa predmetiRepository;
    private final AdminiJpa adminiRepository;

    public PredmetiServiceImpl(PredmetiJpa predmetiRepository, AdminiJpa adminiRepository) {
        this.predmetiRepository = predmetiRepository;
        this.adminiRepository = adminiRepository;
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

    @Override
    public Predmeti create(String ime, List<Integer> idAdmin) {
        List<Admini> adminId = this.adminiRepository.findAllById(idAdmin);
        Predmeti predmeti = new Predmeti(ime,adminId);
        return this.predmetiRepository.save(predmeti);
    }
}
