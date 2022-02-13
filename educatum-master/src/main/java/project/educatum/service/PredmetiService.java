package project.educatum.service;

import project.educatum.model.Admini;
import project.educatum.model.Predmeti;

import java.util.List;
import java.util.Optional;

public interface PredmetiService {
    List<Predmeti> findAll();

    List<Predmeti> findAllByNameLike(String ime);

    void delete(Integer id);

    Optional<Predmeti> findById(Integer id);

    List<Predmeti> findAllByNameAndTeacherLike(String ime, List<Predmeti> predmeti);

    Predmeti create(String ime);


}
