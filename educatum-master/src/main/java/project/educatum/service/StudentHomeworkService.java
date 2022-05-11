package project.educatum.service;

import project.educatum.model.relations.StudentHomeworkRelation;

public interface StudentHomeworkService {

    void updateDone(Integer homeworkID);

    StudentHomeworkRelation findById(Integer id);
}
