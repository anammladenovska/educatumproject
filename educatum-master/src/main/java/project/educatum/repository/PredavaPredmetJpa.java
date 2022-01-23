package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.PredavaPredmet;
import project.educatum.model.PredavaPredmetId;

public interface PredavaPredmetJpa extends JpaRepository<PredavaPredmet, PredavaPredmetId> {
}
