package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.TeacherStudentRelation;
import project.educatum.model.TeacherStudentRelationID;
import project.educatum.repository.TeacherStudentRepository;
import project.educatum.service.TeacherStudentService;

import java.util.List;

@Service
public class TeacherStudentServiceImpl implements TeacherStudentService {

    private final TeacherStudentRepository teacherStudentRepository;

    public TeacherStudentServiceImpl(TeacherStudentRepository teacherStudentRepository) {
        this.teacherStudentRepository = teacherStudentRepository;
    }

    @Override
    public TeacherStudentRelation find(Integer idTeacher, Integer studentID) {
        List<TeacherStudentRelation> teachersstudents = teacherStudentRepository.findAll();
        TeacherStudentRelation result = new TeacherStudentRelation();
        for (TeacherStudentRelation p : teachersstudents) {
            TeacherStudentRelationID pId = p.getId();
            if (pId.getidTeacher().equals(idTeacher) && pId.getstudentID().equals(studentID))
                result = p;
        }
        return result;
    }

    @Override
    public List<TeacherStudentRelation> findAll() {
        return teacherStudentRepository.findAll();
    }
}
