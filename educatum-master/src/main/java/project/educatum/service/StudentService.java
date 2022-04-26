package project.educatum.service;

import project.educatum.model.Student;

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

    void addListening(Integer studentID, Integer classID, Integer teacherID);

}
