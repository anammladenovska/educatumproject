package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Admini;
import project.educatum.model.Predmeti;

import java.util.List;

public interface PredmetiJpa extends JpaRepository<Predmeti, Integer> {
    List<Predmeti> findAllByImeLike(String name);
}
