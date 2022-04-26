package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admin;
import project.educatum.model.Subject;
import project.educatum.model.exceptions.AdminNotFoundException;
import project.educatum.model.exceptions.SubjectNotFoundException;
import project.educatum.repository.AdminRepository;
import project.educatum.repository.SubjectRepository;
import project.educatum.service.SubjectService;

import java.util.*;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectsRepository;
    private final AdminRepository adminRepository;

    public SubjectServiceImpl(SubjectRepository subjectsRepository, AdminRepository adminRepository) {
        this.subjectsRepository = subjectsRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Subject> findAll() {
        return this.subjectsRepository.findAll();
    }

    @Override
    public List<Subject> findAllByNameLike(String name) {
        if (name != null) {
            return this.subjectsRepository.findAllByNameContainingIgnoreCase(name);
        } else {
            return this.subjectsRepository.findAll();
        }
    }

    @Override
    public void delete(Integer id) {
        Subject p = subjectsRepository.findById(id).orElseThrow(SubjectNotFoundException::new);
        subjectsRepository.delete(p);
    }

    @Override
    public Optional<Subject> findById(Integer id) {
        return subjectsRepository.findById(id);
    }

    @Override
    public List<Subject> findAllByNameAndTeacherLike(String name, List<Subject> subjects) {
        List<Subject> searchedSubjects = subjectsRepository.findAllByNameContainingIgnoreCase(name);
        Set<Subject> result = new HashSet<>();
        for (Subject p : searchedSubjects) {
            for (Subject p2 : subjects)
                if (p.getId().equals(p2.getId())) result.add(p2);
        }
        return new ArrayList<>(result);
    }

    @Override
    public Subject create(String ime) {
        Admin admin = adminRepository.findById(1).orElseThrow(AdminNotFoundException::new);
        Subject subject = new Subject(ime, admin);
        return this.subjectsRepository.save(subject);
    }

    @Override
    public Subject findByName(String name) {
        return subjectsRepository.findByName(name);
    }
}
