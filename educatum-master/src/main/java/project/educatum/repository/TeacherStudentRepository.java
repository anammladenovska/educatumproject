package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.TeacherStudentRelation;
import project.educatum.model.TeacherStudentRelationID;

@Repository
public interface TeacherStudentRepository extends JpaRepository<TeacherStudentRelation, TeacherStudentRelationID> {
    TeacherStudentRelation findAllById(TeacherStudentRelationID teacherStudentRelationID);
}
