package project.educatum.service;

import java.time.LocalDate;
import java.util.Date;

public interface InterestService {

    void addSubjectStudent(Integer subjectID, Integer ucenikId, LocalDate datum);

}
