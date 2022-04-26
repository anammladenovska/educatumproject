package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.relations.StudentHomeworkRelation;
import project.educatum.model.primarykeys.StudentHomeworkRelationID;

@Repository
public interface StudentHomeworkRepository extends JpaRepository<StudentHomeworkRelation, StudentHomeworkRelationID> {
}
