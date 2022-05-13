package project.educatum.service;

import project.educatum.model.Homework;

import java.util.List;

public interface HomeworkService {

    List<Homework> findAll();

    List<Homework> findAllByDescriptionLike(String desc);

    Homework create(String desc, Integer teacherID, Integer classID);

    Homework findById(Integer id);
}
