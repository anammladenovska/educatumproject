package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Class;
import project.educatum.model.Homework;
import project.educatum.model.Teacher;
import project.educatum.model.exceptions.HomeworkNotFoundException;
import project.educatum.model.exceptions.TeacherNotFoundException;
import project.educatum.repository.AdminRepository;
import project.educatum.repository.ClassRepository;
import project.educatum.repository.HomeworkRepository;
import project.educatum.repository.TeacherRepository;
import project.educatum.service.HomeworkService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRepository classRepository;

    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, AdminRepository adminRepository, TeacherRepository teacherRepository, ClassRepository classRepository) {
        this.homeworkRepository = homeworkRepository;
        this.teacherRepository = teacherRepository;
        this.classRepository = classRepository;
    }

    @Override
    public List<Homework> findAll() {
        return this.homeworkRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Homework::getDescription))
                .collect(Collectors.toList());
    }

    @Override
    public List<Homework> findAllByDescriptionLike(String opis) {
        if(opis != null){
            return this.homeworkRepository.findAllByDescriptionContainingIgnoreCase(opis);
        }
        else{
            return this.homeworkRepository.findAll();
        }
    }

    @Override
    public Homework create(String opis,Integer teacherID, Integer classID) {
        Teacher teacher = teacherRepository.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Class aclass = classRepository.findById(classID).orElseThrow();
        Homework homework = new Homework(opis, teacher, aclass);
        return this.homeworkRepository.save(homework);
    }

    @Override
    public Homework findById(Integer id) {
        return this.homeworkRepository.findById(id).orElseThrow(HomeworkNotFoundException::new);
    }
}