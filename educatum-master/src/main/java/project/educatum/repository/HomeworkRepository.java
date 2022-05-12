package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Homework;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Integer> {
    List<Homework> findAllByDescriptionContainingIgnoreCase(String opis);

    Homework findByDescription(String opis);
}
