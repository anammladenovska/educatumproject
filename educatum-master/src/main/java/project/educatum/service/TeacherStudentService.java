package project.educatum.service;

import project.educatum.model.TeacherStudentRelation;

import java.util.List;

public interface TeacherStudentService {
    TeacherStudentRelation find(Integer idTeacher, Integer studentID);
    List<TeacherStudentRelation> findAll();
}
