package project.educatum.service;

import project.educatum.model.Predmeti;

import java.util.List;
import java.util.Optional;

public interface PredmetiService {
    List<Predmeti> findAll();
    List<Predmeti> findAllByNameLike(String ime);
    Optional<Predmeti> findById(Integer id);
}
