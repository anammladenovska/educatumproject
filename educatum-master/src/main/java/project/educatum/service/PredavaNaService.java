package project.educatum.service;

import project.educatum.model.PredavaNa;

import java.util.List;

public interface PredavaNaService {
    PredavaNa find(Integer idNastavnik, Integer idUcenik);
    List<PredavaNa> findAll();
}
