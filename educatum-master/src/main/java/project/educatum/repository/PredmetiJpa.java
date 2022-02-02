package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Predmeti;

import java.util.List;
import java.util.Optional;

@Repository
public interface PredmetiJpa extends JpaRepository<Predmeti, Integer> {
    List<Predmeti> findAllByImeContainingIgnoreCase(String ime);
    Optional<Predmeti> findById(Integer id);
}
