package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Predmeti;

public interface PredmetiJpa extends JpaRepository<Predmeti, Integer> {
}
