package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.UceniciDomasni;
import project.educatum.model.UceniciDomasniId;

public interface UceniciDomasniJpa extends JpaRepository<UceniciDomasni, UceniciDomasniId> {
}
