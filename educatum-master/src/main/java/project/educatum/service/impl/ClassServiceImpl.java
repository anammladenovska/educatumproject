package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Class;
import project.educatum.model.Teacher;
import project.educatum.model.Subject;
import project.educatum.model.exceptions.SubjectNotFoundException;
import project.educatum.model.exceptions.TeacherNotFoundException;
import project.educatum.repository.ClassRepository;
import project.educatum.repository.TeacherRepository;
import project.educatum.repository.SubjectRepository;
import project.educatum.service.ClassService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public ClassServiceImpl(ClassRepository classRepository, TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.classRepository = classRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<Class> findAll() {
        return classRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Class::getBeginningTime))
                .collect(Collectors.toList());
    }

    @Override
    public void addClass(LocalDateTime dateTime, String desc, Integer teacherID, Integer subjectID) {
        Teacher n = teacherRepository.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Subject p = subjectRepository.findById(subjectID).orElseThrow(SubjectNotFoundException::new);
        Class c = new Class(dateTime, desc, n, p);
        classRepository.save(c);
    }

    @Override
    public List<Class> findAllByTeacher(Integer id) {
        return classRepository
                .findAllByIdTeacher(id)
                .stream()
                .sorted(Comparator.comparing(Class::getBeginningTime))
                .collect(Collectors.toList());
    }

    @Override
    public Class findByTopic(String tema) {
        return classRepository.findByTopic(tema);
    }

}
