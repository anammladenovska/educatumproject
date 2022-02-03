package project.educatum.service;

import java.time.LocalDate;
import java.util.Date;

public interface ZainteresiraniZaService {
    void addSubjectStudent(Integer predmetId, Integer ucenikId, LocalDate datum);
}
