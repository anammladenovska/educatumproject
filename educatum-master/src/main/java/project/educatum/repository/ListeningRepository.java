package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Listening;
import project.educatum.model.ListeningID;
import project.educatum.model.Payment;

import java.util.List;

@Repository
public interface ListeningRepository extends JpaRepository<Listening, ListeningID> {
    @Query(value = "select * from project.slusanje s where s.id_cas = :idClass and s.id_ucenik = :idStudent",
            nativeQuery = true)
    List<Listening> findAllByClassAndStudent(Integer idClass, Integer idStudent);
}
