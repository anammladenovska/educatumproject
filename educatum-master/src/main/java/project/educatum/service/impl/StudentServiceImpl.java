package project.educatum.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.*;
import project.educatum.model.exceptions.*;
import project.educatum.model.primarykeys.InterestID;
import project.educatum.model.primarykeys.ListeningID;
import project.educatum.repository.*;
import project.educatum.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentsRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final TeacherRepository teachersRepository;
    private final InterestRepository interestRepository;
    private static Integer counter = 0;
    private final ListeningRepository listeningRepository;
    private final PaymentRepository paymentRepository;

    public StudentServiceImpl(StudentRepository studentsRepository, PasswordEncoder passwordEncoder, AdminRepository adminRepository, TeacherRepository teachersRepository, InterestRepository interestRepository, ListeningRepository listeningRepository, PaymentRepository paymentRepository) {
        this.studentsRepository = studentsRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.teachersRepository = teachersRepository;
        this.interestRepository = interestRepository;
        this.listeningRepository = listeningRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void register(String name, String surname, String email, String password, String repeatPassword, String telNum, String desc) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();

        for (Teacher n : teachersRepository.findAll())
            if (n.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");

        for (Student u : studentsRepository.findAll())
            if (u.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");

        for (Admin a : adminRepository.findAll())
            if (a.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");

        Student user = new Student(desc, name, surname, email, passwordEncoder.encode(password), telNum);
        studentsRepository.save(user);
    }

    @Override
    public List<Student> findAll() {
        return studentsRepository.findAll();
    }

    @Override
    public Student findByEmail(String email) {
        return studentsRepository.findByEmail(email);
    }

    @Override
    public List<Student> findAllByNameLike(String name, List<Student> students) {
        List<Student> searchedStudents = studentsRepository.findAllByNameContainingIgnoreCase(name);
        Set<Student> result = new HashSet<>();
        for (Student u : searchedStudents) {
            for (Student u2 : students)
                if (u.getId().equals(u2.getId())) result.add(u2);
        }
        return new ArrayList<>(result);
    }

    @Override
    public void delete(Integer id) {
        Student u = studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentsRepository.delete(u);
    }

    @Override
    public Student findById(Integer id) {
        return studentsRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    @Override
    public List<Student> findAllByName(String name) {
        return studentsRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public void interestedIn(Integer subjectId, Integer studentId) {
        InterestID zId = new InterestID(subjectId, studentId);
        Interest z = new Interest(zId, LocalDate.now());
        interestRepository.save(z);
    }

    @Override
    public void addListening(Integer studentID, Integer classID, Integer teacherID) {
        Teacher t = teachersRepository.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Payment p = new Payment(0, t);
        paymentRepository.save(p);
        Student s = studentsRepository.findById(studentID).orElseThrow(StudentNotFoundException::new);
        ListeningID listeningID = new ListeningID(classID, counter++);
        Listening l = new Listening(listeningID, p, s, false);
        listeningRepository.save(l);
    }

}
