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
            if (n.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for (Student u : studentsRepository.findAll()) {
            if (u.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for (Admin a : adminRepository.findAll()) {
            if (a.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
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
        return classesRepository.findAllByIdTeacher(id);
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
        List<Student> students = new ArrayList<>();
        List<TeacherStudentRelation> teacherStudentRelationList = teacherStudentRepository.findAll();
        for (TeacherStudentRelation p : teacherStudentRelationList) {
            TeacherStudentRelationID pId = p.getId();
            if (pId.getTeacherID().equals(id)) {
                Integer studentID = pId.getStudentID();
                students.add(studentsRepository.findById(studentID).orElseThrow(StudentNotFoundException::new));
            }
        }
        return students;
    }

    @Override
    public List<Teacher> getAllTeachersBySubject(Integer id) {
        List<Teacher> teachers = new ArrayList<>();
        List<TeacherSubjectRelation> teacherSubjectRelationList = teacherSubjectRepository.findAll();
        for (TeacherSubjectRelation pp : teacherSubjectRelationList) {
            TeacherSubjectRelationID ppId = pp.getId();
            if (ppId.getSubjectID().equals(id)) {
                Integer idTeacher = ppId.getTeacherID();
                teachers.add(teachersRepository.findById(idTeacher).orElseThrow(TeacherNotFoundException::new));
            }
        }
        return teachers;
    }

    @Override
    public Teacher edit(Integer id, String opis) {
        Teacher teacher = this.findById(id);
        teacher.setDescription(opis);
        return this.teachersRepository.save(teacher);
    }

    @Override
    public void updateEnabled(Integer teacherID) {
        teachersRepository.updateEnabled(teacherID);
    }

    @Override
    public List<Subject> getSubjectsByTeacher(Integer id) {
        List<Subject> subjects = new ArrayList<>();
        List<TeacherSubjectRelation> teacherSubjectRelationList = teacherSubjectRepository.findAll();
        for (TeacherSubjectRelation teachesSubject : teacherSubjectRelationList) {
            TeacherSubjectRelationID ppId = teachesSubject.getId();
            if (ppId.getTeacherID().equals(id)) {
                Integer subjectID = ppId.getSubjectID();
                subjects.add(subjectRepository.findById(subjectID).orElseThrow(SubjectNotFoundException::new));
            }
        }
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


}
