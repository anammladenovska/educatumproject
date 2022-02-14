package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Listening;
import project.educatum.model.ListeningID;
@Repository
public interface ListeningRepository extends JpaRepository<Listening, ListeningID> {
}
