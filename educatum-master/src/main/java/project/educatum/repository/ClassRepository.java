package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Class;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer> {
    @Query(value = "select * from project.casovi c where c.id_nastavnik = :idTeacher", nativeQuery = true)
    List<Class> findAllByIdTeacher(Integer idTeacher);

    Class findByTopic(String tema);
}
