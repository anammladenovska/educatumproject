package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Interest;
import project.educatum.model.InterestID;
@Repository
public interface InterestRepository extends JpaRepository<Interest, InterestID> {
}
