package project.educatum.service;

import project.educatum.model.Class;

import java.time.LocalDateTime;
import java.util.List;

public interface ClassService {

    List<Class> findAll();

    void addClass(LocalDateTime dateTime, String desc, Integer teacherID, Integer subjectID);

    List<Class> findAllByTeacher(Integer id);
}
