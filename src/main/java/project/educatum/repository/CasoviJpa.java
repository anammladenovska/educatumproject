package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Casovi;

public interface CasoviJpa extends JpaRepository<Casovi,Integer> {
}
