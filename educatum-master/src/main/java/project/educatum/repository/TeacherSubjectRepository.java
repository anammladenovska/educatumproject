package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.TeacherSubjectRelation;
import project.educatum.model.TeacherSubjectRelationID;

@Repository
public interface TeacherSubjectRepository extends JpaRepository<TeacherSubjectRelation, TeacherSubjectRelationID> {
}
