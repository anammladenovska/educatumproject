package project.educatum.service;

import project.educatum.model.Ucenici;

import java.util.List;

public interface UceniciService {
    void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis);

    List<Ucenici> findAll();

    Ucenici findByEmail(String email);

    List<Ucenici> findAllByNameLike(String ime, List<Ucenici> ucenici);

    void delete(Integer id);

    Ucenici findById(Integer id);

    List<Ucenici> findAllByName(String ime);

    void interestedIn(Integer subjectId, Integer studentId);
}
