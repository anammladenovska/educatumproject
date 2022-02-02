package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Kvalifikacii;
@Repository
public interface KvalifikaciiJpa extends JpaRepository<Kvalifikacii,Integer> {
}
