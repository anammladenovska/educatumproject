package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.UceniciDomasni;
import project.educatum.model.UceniciDomasniId;
@Repository
public interface UceniciDomasniJpa extends JpaRepository<UceniciDomasni, UceniciDomasniId> {
}
