package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.PredavaPredmet;
import project.educatum.model.PredavaPredmetId;
@Repository
public interface PredavaPredmetJpa extends JpaRepository<PredavaPredmet, PredavaPredmetId> {
}
