package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.PredmetiJpa;
import project.educatum.service.PredmetiService;

import java.util.*;

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
        if (ime != null) {
            return this.predmetiRepository.findAllByImeContainingIgnoreCase(ime);
        } else {
            return this.predmetiRepository.findAll();
        }
    }

    @Override
    public Optional<Predmeti> findById(Integer id) {
        return predmetiRepository.findById(id);
    }

    @Override
    public List<Predmeti> findAllByNameAndTeacherLike(String ime, List<Predmeti> predmeti) {
        List<Predmeti> searchedSubjects = predmetiRepository.findAllByImeContainingIgnoreCase(ime);
        Set<Predmeti> result = new HashSet<>();
        for (Predmeti p : searchedSubjects) {
            for (Predmeti p2 : predmeti)
                if (p.getId().equals(p2.getId())) result.add(p2);
        }
        return new ArrayList<>(result);
    }

    @Override
    public Predmeti create(String ime, List<Integer> idAdmin) {
        List<Admini> adminId = this.adminiRepository.findAllById(idAdmin);
        Predmeti predmeti = new Predmeti(ime, adminId);
        return this.predmetiRepository.save(predmeti);
    }
}
