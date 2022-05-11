package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.relations.StudentHomeworkRelation;
import project.educatum.model.primarykeys.StudentHomeworkRelationID;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StudentHomeworkRepository extends JpaRepository<StudentHomeworkRelation, StudentHomeworkRelationID> {

    @Transactional
    @Modifying
    @Query("UPDATE StudentHomeworkRelation h SET h.isDone=true WHERE h.id=:id")
    void updateDone(Integer id);

    StudentHomeworkRelation findById(Integer id);
}
