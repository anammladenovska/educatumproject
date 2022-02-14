package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Teacher;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByEmail(String email);

    List<Teacher> findAllByImeContainingIgnoreCase(String ime);

    @Transactional
    @Modifying
    @Query("UPDATE Teacher n SET n.enabled=true WHERE n.id=:id")
    void updateEnabled(Integer id);
}
