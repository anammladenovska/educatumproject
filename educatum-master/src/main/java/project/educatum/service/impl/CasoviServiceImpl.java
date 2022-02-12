package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Casovi;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.exceptions.SubjectNotFoundException;
import project.educatum.model.exceptions.TeacherNotFoundException;
import project.educatum.repository.CasoviJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.PredmetiJpa;
import project.educatum.service.CasoviService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CasoviServiceImpl implements CasoviService {

    private final CasoviJpa casoviJpa;
    private final NastavniciJpa nastavniciJpa;
    private final PredmetiJpa predmetiJpa;

    public CasoviServiceImpl(CasoviJpa casoviJpa, NastavniciJpa nastavniciJpa, PredmetiJpa predmetiJpa) {
        this.casoviJpa = casoviJpa;
        this.nastavniciJpa = nastavniciJpa;
        this.predmetiJpa = predmetiJpa;
    }

    @Override
    public List<Casovi> findAll() {
        return casoviJpa.findAll();
    }

    @Override
    public void addClass(LocalDateTime dateTime, String desc, Integer teacherID, Integer subjectID){
        Nastavnici n = nastavniciJpa.findById(teacherID).orElseThrow(TeacherNotFoundException::new);
        Predmeti p = predmetiJpa.findById(subjectID).orElseThrow(SubjectNotFoundException::new);
        Casovi c = new Casovi(dateTime,desc,n,p);
        casoviJpa.save(c);
    }

    @Override
    public List<Casovi> findAllByNastavnik(Integer id) {
        return casoviJpa.findAllByIdNastavnik(id);
    }


}
