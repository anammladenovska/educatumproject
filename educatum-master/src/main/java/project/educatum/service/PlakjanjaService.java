package project.educatum.service;

import project.educatum.model.Nastavnici;

import java.util.HashMap;
import java.util.List;

public interface PlakjanjaService {

    List<Object[]> getPaymentsQuery();

    Integer studentTeacherLoan(Integer studentId, Integer teacherId);

    Integer brojSlusaniCasovi(Integer idUcenik, Integer idNastavnik);

    List<Object[]> getListenedClassesQuery();
}
