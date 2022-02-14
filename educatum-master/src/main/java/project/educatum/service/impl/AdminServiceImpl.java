package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admin;
import project.educatum.repository.AdminRepository;
import project.educatum.repository.TeacherRepository;
import project.educatum.repository.StudentRepository;
import project.educatum.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final TeacherRepository teachersRepository;
    private final StudentRepository studentsRepository;

    public AdminServiceImpl(AdminRepository adminRepository, TeacherRepository teachersRepository, StudentRepository studentsRepository) {
        this.adminRepository = adminRepository;
        this.teachersRepository = teachersRepository;
        this.studentsRepository = studentsRepository;
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public List<Admin> listAll() {
        return this.adminRepository.findAll();
    }
}
