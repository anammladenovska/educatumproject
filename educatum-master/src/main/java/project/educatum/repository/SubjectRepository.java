package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    List<Subject> findAllByNameContainingIgnoreCase(String name);

    Optional<Subject> findById(Integer id);

    Subject findByName(String name);
}
