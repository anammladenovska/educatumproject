package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.relations.TeacherStudentRelation;
import project.educatum.model.primarykeys.TeacherStudentRelationID;
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
        List<TeacherStudentRelation> teacherStudentRelationList = teacherStudentRepository.findAll();
        TeacherStudentRelation result = new TeacherStudentRelation();
        for (TeacherStudentRelation p : teacherStudentRelationList) {
            TeacherStudentRelationID pId = p.getId();
            if (pId.getTeacherID().equals(idTeacher) && pId.getStudentID().equals(studentID))
                result = p;
        }
        return result;
    }

    @Override
    public List<TeacherStudentRelation> findAll() {
        return teacherStudentRepository.findAll();
    }
}
