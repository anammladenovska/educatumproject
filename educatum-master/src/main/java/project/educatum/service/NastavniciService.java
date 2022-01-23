package project.educatum.service;

import project.educatum.model.Nastavnici;

import java.util.List;

public interface NastavniciService {
    List<Nastavnici> findAll();
    void register(String ime, String prezime, String email, String password,String repeatPassword, String telBroj, String opis);
}
