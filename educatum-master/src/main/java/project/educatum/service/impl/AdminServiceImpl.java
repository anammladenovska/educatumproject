package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admin;
import project.educatum.repository.AdminRepository;
import project.educatum.service.AdminService;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
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
