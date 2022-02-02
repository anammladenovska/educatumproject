package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Casovi;
@Repository
public interface CasoviJpa extends JpaRepository<Casovi,Integer> {
}
