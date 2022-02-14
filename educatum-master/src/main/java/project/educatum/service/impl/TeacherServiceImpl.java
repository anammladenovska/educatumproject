package project.educatum.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.*;
import project.educatum.model.Class;
import project.educatum.model.exceptions.*;
import project.educatum.repository.*;
import project.educatum.service.TeacherService;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teachersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final StudentRepository studentsRepository;
    private final TeacherStudentRepository teacherStudentRepository;
    private final TeacherSubjectRepository predavaPredmetRepository;
    private final SubjectRepository subjectRepository;
    private final PaymentRepository paymentRepository;
    private final ClassRepository classesRepository;

    public TeacherServiceImpl(TeacherRepository teachersRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository, StudentRepository studentsRepository, TeacherStudentRepository teacherStudentRepository, TeacherSubjectRepository predavaPredmetRepository, SubjectRepository subjectRepository, PaymentRepository paymentRepository, ClassRepository classesRepository) {
        this.teachersRepository = teachersRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.studentsRepository = studentsRepository;
        this.teacherStudentRepository = teacherStudentRepository;
        this.predavaPredmetRepository = predavaPredmetRepository;
        this.subjectRepository = subjectRepository;
        this.paymentRepository = paymentRepository;
        this.classesRepository = classesRepository;
    }

    @Override
    public List<Teacher> findAll() {
        return teachersRepository.findAll();
    }

    @Override
    public void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis) {
        if(email==null || email.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        if(!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();

        for(Teacher n : teachersRepository.findAll()){
            if(n.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for(Student u : studentsRepository.findAll()){
            if(u.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for(Admin a : adminRepository.findAll()){
            if(a.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }

        Teacher user = new Teacher( ime,prezime,opis,email,passwordEncoder.encode(password),telBroj);
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
    public List<Teacher> findAllByNameLike(String ime) {
        return teachersRepository.findAllByImeContainingIgnoreCase(ime);
    }

    @Override
    public void delete(Integer id) {
        Teacher n = teachersRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
        teachersRepository.delete(n);
    }

    @Override
    public List<Student> getStudentsByTeacher(Integer id){
        List<Student> students = new ArrayList<>();
        List<TeacherStudentRelation> teachersstudents = teacherStudentRepository.findAll();
        for(TeacherStudentRelation p : teachersstudents){
            TeacherStudentRelationID pId = p.getId();
            if(pId.getidTeacher().equals(id)){
                Integer studentID = pId.getstudentID();
                students.add(studentsRepository.findById(studentID).orElseThrow(StudentNotFoundException::new));
            }
        }
        return students;
    }

    @Override
    public List<Teacher> getAllTeachersBySubject(Integer id) {
        List<Teacher> teachers = new ArrayList<>();
        List<TeacherSubjectRelation> teachesSubject = predavaPredmetRepository.findAll();
        for(TeacherSubjectRelation pp : teachesSubject){
            TeacherSubjectRelationID ppId = pp.getId();
            if(ppId.getsubjectID().equals(id)){
                Integer idTeacher = ppId.getidTeacher();
                teachers.add(teachersRepository.findById(idTeacher).orElseThrow(TeacherNotFoundException::new));
            }
        }
        return teachers;
    }

    @Override
    public void updateEnabled(Integer teacherID){
        teachersRepository.updateEnabled(teacherID);
    }

    @Override
    public List<Subject> getSubjectsByTeacher(Integer id) {
        List<Subject> subjects = new ArrayList<>();
         List<TeacherSubjectRelation> teacherssubjects = predavaPredmetRepository.findAll();
        for(TeacherSubjectRelation teachesSubject : teacherssubjects){
            TeacherSubjectRelationID ppId = teachesSubject.getId();
            if(ppId.getidTeacher().equals(id)){
                Integer subjectID = ppId.getsubjectID();
                subjects.add(subjectRepository.findById(subjectID).orElseThrow(SubjectNotFoundException::new));
            }
        }
        return subjects;
    }

    @Override
    public void addPayment(Integer teacherId, Integer price){
        Teacher n = teachersRepository.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
        Payment p = new Payment(price,n);
        paymentRepository.save(p);
    }

    @Override
    public Teacher findById(Integer id) {
        return teachersRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
    }

    @Override
    public void addSubject(Integer teacherId, Integer subjectId, String desc){
        Teacher nastavnik = teachersRepository.findById(teacherId).orElseThrow(TeacherNotFoundException::new);
        Subject predmet = subjectRepository.findById(subjectId).orElseThrow(SubjectNotFoundException::new);

        TeacherSubjectRelationID ppId = new TeacherSubjectRelationID(teacherId,subjectId);
        predavaPredmetRepository.save(new TeacherSubjectRelation(ppId,desc));

    }


    @Override
    public void addStudent(Integer teacherID, Integer ucenikId, Integer priceByClass, Integer numScheduledClasses) {
       Teacher nastavnik = teachersRepository.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Student ucenik = studentsRepository.findById(ucenikId).orElseThrow(StudentNotFoundException::new);

        TeacherStudentRelationID pId = new TeacherStudentRelationID(nastavnik.getId(),ucenik.getId());
        teacherStudentRepository.save(new TeacherStudentRelation(pId, priceByClass, numScheduledClasses));
    }



}
