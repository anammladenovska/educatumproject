package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.PredavaNa;
import project.educatum.model.PredavaNaId;

public interface PredavaNaJpa extends JpaRepository<PredavaNa, PredavaNaId> {
}
