package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;

import java.util.List;

@Repository
public interface UceniciJpa extends JpaRepository<Ucenici, Integer> {
    Ucenici findByEmail(String email);

    Ucenici findByEmailAndPassword(String email, String password);

    List<Ucenici> findAllByImeContainingIgnoreCase(String ime);
}
