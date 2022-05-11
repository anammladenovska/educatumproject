package project.educatum.service;

import project.educatum.model.Homework;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface HomeworkService {

    List<Homework> findAll();

    List<Homework> findAllByDescriptionLike(String opis);

    Homework create(String opis,Integer teacherID, Integer classID);

    Homework findById(Integer id);
}
