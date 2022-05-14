package project.educatum.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.*;
import project.educatum.model.Class;
import project.educatum.model.exceptions.*;
import project.educatum.model.primarykeys.TeacherStudentRelationID;
import project.educatum.model.primarykeys.TeacherSubjectRelationID;
import project.educatum.model.relations.TeacherStudentRelation;
import project.educatum.model.relations.TeacherSubjectRelation;
import project.educatum.repository.*;
import project.educatum.service.TeacherService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teachersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final StudentRepository studentsRepository;
    private final TeacherStudentRepository teacherStudentRepository;
    private final TeacherSubjectRepository teacherSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final PaymentRepository paymentRepository;
    private final ClassRepository classesRepository;
    private final ListeningRepository listeningRepository;


    public TeacherServiceImpl(TeacherRepository teachersRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository, StudentRepository studentsRepository, TeacherStudentRepository teacherStudentRepository, TeacherSubjectRepository teacherSubjectRepository, SubjectRepository subjectRepository, PaymentRepository paymentRepository, ClassRepository classesRepository, ListeningRepository listeningRepository) {
        this.teachersRepository = teachersRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.studentsRepository = studentsRepository;
        this.teacherStudentRepository = teacherStudentRepository;
        this.teacherSubjectRepository = teacherSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.paymentRepository = paymentRepository;
        this.classesRepository = classesRepository;
        this.listeningRepository = listeningRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teachersRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Teacher::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        if (!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();

        for (Teacher n : teachersRepository.findAll()) {
            if (n.getEmail().equals(email)) {
                throw new UsernameAlreadyExistsException("Username already exists!");
            }
        }
        for (Student u : studentsRepository.findAll()) {
            if (u.getEmail().equals(email)) {
                throw new UsernameAlreadyExistsException("Username already exists!");
            }
        }
        for (Admin a : adminRepository.findAll()) {
            if (a.getEmail().equals(email)) {
                throw new UsernameAlreadyExistsException("Username already exists!");
            }
        }

        Teacher user = new Teacher(ime, prezime, opis, email, passwordEncoder.encode(password), telBroj);
        user.setIdAdmin(adminRepository.findAll().get(0));
        teachersRepository.save(user);
    }

    @Override
    public Teacher findByEmail(String email) {
        return teachersRepository.findByEmail(email);
    }

    @Override
    public List<Class> getClassesByTeacher(Integer id) {
        return classesRepository.findAllByIdTeacher(id)
                .stream()
                .sorted(Comparator.comparing(Class::beginningDate))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Teacher teacher){
        this.teachersRepository.save(teacher);
    }

    @Override
    public List<Teacher> findAllByNameLike(String name) {
        return teachersRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public void delete(Integer id) {
        Teacher n = teachersRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
        teachersRepository.delete(n);
    }

    @Override
    public List<Student> getStudentsByTeacher(Integer id) {
        List<Student> students;
        List<TeacherStudentRelation> teacherStudentRelationList = teacherStudentRepository.findAll();
        students = teacherStudentRelationList
                .stream()
                .map(TeacherStudentRelation::getId)
                .filter(pId -> pId.getTeacherID().equals(id))
                .map(TeacherStudentRelationID::getStudentID)
                .map(studentID -> studentsRepository.findById(studentID).orElseThrow(StudentNotFoundException::new))
                .collect(Collectors.toList());
        return students;
    }

    @Override
    public List<Teacher> getAllTeachersBySubject(Integer id) {
        List<Teacher> teachers;
        List<TeacherSubjectRelation> teacherSubjectRelationList = teacherSubjectRepository.findAll();
        teachers = teacherSubjectRelationList
                .stream()
                .map(TeacherSubjectRelation::getId)
                .filter(ppId -> ppId.getSubjectID().equals(id))
                .map(TeacherSubjectRelationID::getTeacherID)
                .map(idTeacher -> teachersRepository.findById(idTeacher).orElseThrow(TeacherNotFoundException::new))
                .collect(Collectors.toList());
        return teachers;
    }

    @Override
    public Teacher edit(Integer id, String ime, String prezime,String description, String email, String telephoneNumber) {
        Teacher teacher = this.findById(id);
        teacher.setName(ime);
        teacher.setSurname(prezime);
        teacher.setDescription(description);
        teacher.setEmail(email);
        teacher.setTelephoneNumber(telephoneNumber);
        return this.teachersRepository.save(teacher);
    }

    @Override
    public void updateEnabled(Integer teacherID) {
        teachersRepository.updateEnabled(teacherID);
    }

    @Override
    public List<Subject> getSubjectsByTeacher(Integer id) {
        List<Subject> subjects;
        List<TeacherSubjectRelation> teacherSubjectRelationList = teacherSubjectRepository.findAll();
        subjects = teacherSubjectRelationList
                .stream()
                .map(TeacherSubjectRelation::getId)
                .filter(ppId -> ppId.getTeacherID().equals(id))
                .map(TeacherSubjectRelationID::getSubjectID)
                .map(subjectID -> subjectRepository.findById(subjectID).orElseThrow(SubjectNotFoundException::new))
                .collect(Collectors.toList());
        return subjects;
    }

    @Override
    public Teacher findById(Integer id) {
        return teachersRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
    }

    @Override
    public void addSubject(Integer teacherId, Integer subjectId, String desc) {
        TeacherSubjectRelationID ppId = new TeacherSubjectRelationID(teacherId, subjectId);
        teacherSubjectRepository.save(new TeacherSubjectRelation(ppId, desc));
    }


    @Override
    public void addStudent(Integer teacherID, Integer studentID, Integer priceByClass, Integer numScheduledClasses) {
        Teacher teacher = teachersRepository.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Student student = studentsRepository.findById(studentID).orElseThrow(StudentNotFoundException::new);
        TeacherStudentRelationID pId = new TeacherStudentRelationID(teacher.getId(), student.getId());
        teacherStudentRepository.save(new TeacherStudentRelation(pId, priceByClass, numScheduledClasses));
    }


    @Override
    public double getRatingForTeacher(Long teacherID) {
        List<TeacherStudentRelation> teacherStudentRelationList = teacherStudentRepository
                .findAll()
                .stream()
                .filter(t -> t.getId().getTeacherID().equals(teacherID.intValue()))
                .filter(TeacherStudentRelation::hasRated)
                .collect(Collectors.toList());
        float rating = 0;
        for (TeacherStudentRelation t : teacherStudentRelationList) {
            rating += t.getRating();
        }
        return rating / teacherStudentRelationList.size() * 1.0;
    }
}