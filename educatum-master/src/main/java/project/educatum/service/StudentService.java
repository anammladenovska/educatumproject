package project.educatum.service;

import project.educatum.model.Student;
import project.educatum.model.Teacher;

import java.util.List;

public interface StudentService {

    void register(String name, String surname, String email, String password, String repeatPassword, String telNum, String desc);

    List<Student> findAll();

    Student findByEmail(String email);

    List<Student> findAllByNameLike(String ime, List<Student> students);

    void delete(Integer id);

    Student findById(Integer id);

    List<Student> findAllByName(String ime);

    void interestedIn(Integer subjectId, Integer studentId);

    void save(Student student);

    void addListening(Integer studentID, Integer classID, Integer teacherID);

    boolean rateTeacher(Teacher t, Student s, Float r);

    Student edit(Integer id, String ime, String prezime, String opis, String email, String telefonskiBroj);
}
