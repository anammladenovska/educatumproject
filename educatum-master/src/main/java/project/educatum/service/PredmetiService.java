package project.educatum.service;

import project.educatum.model.Predmeti;

import java.util.List;

public interface PredmetiService {
    List<Predmeti> findAll();
    List<Predmeti> findAllByNameLike(String imePredmet);
}
