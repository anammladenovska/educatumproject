package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Qualification;
@Repository
public interface QualificationRepository extends JpaRepository<Qualification,Integer> {
}
