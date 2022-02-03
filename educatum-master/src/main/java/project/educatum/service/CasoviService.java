package project.educatum.service;

import project.educatum.model.Casovi;

import java.util.List;

public interface CasoviService {
    List<Casovi> findAll();
    List<Casovi> findAllByNastavnik(Integer id);
}
