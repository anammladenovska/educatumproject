package project.educatum.service;

import project.educatum.model.Class;
import project.educatum.model.Student;
import project.educatum.model.Teacher;
import project.educatum.model.Subject;

import java.util.List;

public interface TeacherService {

    List<Teacher> findAll();

    void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis);

    List<Student> getStudentsByTeacher(Integer id);


    void updateEnabled(Integer teacherID);

    List<Subject> getSubjectsByTeacher(Integer id);

    Teacher findById(Integer id);

    Teacher findByEmail(String email);

    List<Class> getClassesByTeacher(Integer id);

    List<Teacher> findAllByNameLike(String ime);

    void delete(Integer id);

    void addSubject(Integer teacherId, Integer subjectId, String desc);

    void addStudent(Integer teacherID, Integer ucenikId, Integer priceByClass, Integer numScheduledClasses);

    List<Teacher> getAllTeachersBySubject(Integer id);

}
