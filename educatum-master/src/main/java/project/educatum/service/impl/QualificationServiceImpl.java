package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admin;
import project.educatum.model.Qualification;
import project.educatum.model.Teacher;
import project.educatum.model.exceptions.AdminNotFoundException;
import project.educatum.model.exceptions.TeacherNotFoundException;
import project.educatum.repository.AdminRepository;
import project.educatum.repository.QualificationRepository;
import project.educatum.repository.TeacherRepository;
import project.educatum.service.QualificationService;

@Service
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;

    public QualificationServiceImpl(QualificationRepository qualificationRepository, TeacherRepository teacherRepository, AdminRepository adminRepository) {
        this.qualificationRepository = qualificationRepository;
        this.teacherRepository = teacherRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public void insert(String document,Integer idTeacher) {
        Teacher n = teacherRepository.findById(idTeacher).orElseThrow(TeacherNotFoundException::new);
        Admin a = adminRepository.findById(1).orElseThrow(AdminNotFoundException::new);
        Qualification kvalifikacija = new Qualification();
        kvalifikacija.setDokument(document);
        kvalifikacija.setIdAdmin(a);
        kvalifikacija.setidTeacher(n);
        qualificationRepository.save(kvalifikacija);
    }

}
