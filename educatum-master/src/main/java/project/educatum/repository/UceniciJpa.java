package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Ucenici;

public interface UceniciJpa extends JpaRepository<Ucenici,Integer> {
Ucenici findByEmail(String email);
}
