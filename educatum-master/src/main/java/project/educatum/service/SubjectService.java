package project.educatum.service;

import project.educatum.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> findAll();

    List<Subject> findAllByNameLike(String ime);

    void delete(Integer id);

    Optional<Subject> findById(Integer id);

    List<Subject> findAllByNameAndTeacherLike(String ime, List<Subject> subjects);

    Subject create(String ime);

    Subject findByName(String ime);
}
