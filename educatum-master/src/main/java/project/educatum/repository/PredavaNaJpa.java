package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.PredavaNa;
import project.educatum.model.PredavaNaId;

@Repository
public interface PredavaNaJpa extends JpaRepository<PredavaNa, PredavaNaId> {
}
