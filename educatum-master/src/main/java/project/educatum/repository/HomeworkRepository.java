package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Integer> {
}
