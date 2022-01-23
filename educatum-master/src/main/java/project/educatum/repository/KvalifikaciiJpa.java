package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Kvalifikacii;

public interface KvalifikaciiJpa extends JpaRepository<Kvalifikacii,Integer> {
}
