package project.educatum.service;

import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;

import java.util.List;

public interface NastavniciService {
    List<Nastavnici> findAll();
    void register(String ime, String prezime, String email, String password,String repeatPassword, String telBroj, String opis);
    List<Ucenici> getStudentsByTeacher(Integer id);
    Nastavnici findById(Integer id);
    Nastavnici findByEmail(String email);
    void addStudent(Integer nastavnikId, Integer ucenikId, Integer cenaPoCas, Integer brojCasoviPoDogovor);
}
