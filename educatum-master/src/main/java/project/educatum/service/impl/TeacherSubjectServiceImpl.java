package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.relations.TeacherSubjectRelation;
import project.educatum.model.primarykeys.TeacherSubjectRelationID;
import project.educatum.repository.TeacherSubjectRepository;
import project.educatum.service.TeacherSubjectService;

@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService {

    private final TeacherSubjectRepository teacherSubjectRepository;

    public TeacherSubjectServiceImpl(TeacherSubjectRepository teacherSubjectRepository) {
        this.teacherSubjectRepository = teacherSubjectRepository;
    }

    @Override
    public void addSubject(Integer teacherID, Integer subjectID, String desc) {
        TeacherSubjectRelationID teacherSubjectRelationID = new TeacherSubjectRelationID(teacherID, subjectID);
        TeacherSubjectRelation t = new TeacherSubjectRelation(teacherSubjectRelationID, desc);
        teacherSubjectRepository.save(t);

    }

}
