package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.exceptions.HomeworkNotFoundException;
import project.educatum.model.relations.StudentHomeworkRelation;
import project.educatum.repository.StudentHomeworkRepository;
import project.educatum.service.StudentHomeworkService;

@Service
public class StudentHomeworkServiceImpl implements StudentHomeworkService {

    private final StudentHomeworkRepository studentHomeworkRepository;

    public StudentHomeworkServiceImpl(StudentHomeworkRepository studentHomeworkRepository) {
        this.studentHomeworkRepository = studentHomeworkRepository;
    }

    @Override
    public void updateDone(Integer homeworkID) {
        studentHomeworkRepository.updateDone(homeworkID);
    }

    @Override
    public StudentHomeworkRelation findById(Integer id) {
        return this.studentHomeworkRepository.findById(id);
    }
}
