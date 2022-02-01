package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Predmeti;

import java.util.List;
import java.util.Optional;

public interface PredmetiJpa extends JpaRepository<Predmeti, Integer> {
    List<Predmeti> findAllByImeContainingIgnoreCase(String ime);
    Optional<Predmeti> findById(Integer id);
}
