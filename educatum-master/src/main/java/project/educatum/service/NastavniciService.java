package project.educatum.service;

import project.educatum.model.Casovi;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;

import java.util.List;

public interface NastavniciService {
    List<Nastavnici> findAll();

    void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis);

    List<Ucenici> getStudentsByTeacher(Integer id);

    List<Predmeti> getSubjectsByTeacher(Integer id);

    Nastavnici findById(Integer id);

    Nastavnici findByEmail(String email);

    List<Casovi> getClassesByTeacher(Integer id);
    List<Nastavnici> findAllByNameLike(String ime);

    void delete(Integer id);

    void addStudent(Integer nastavnikId, Integer ucenikId, Integer cenaPoCas, Integer brojCasoviPoDogovor);
}
