package project.educatum.service;

import project.educatum.model.Casovi;

import java.time.LocalDateTime;
import java.util.List;

public interface CasoviService {
    List<Casovi> findAll();

    void addClass(LocalDateTime dateTime, String desc, Integer teacherID, Integer subjectID);

    List<Casovi> findAllByNastavnik(Integer id);
}
