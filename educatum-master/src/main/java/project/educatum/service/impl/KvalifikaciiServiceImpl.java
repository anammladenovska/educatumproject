package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Kvalifikacii;
import project.educatum.model.Nastavnici;
import project.educatum.model.exceptions.AdminNotFoundException;
import project.educatum.model.exceptions.TeacherNotFoundException;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.KvalifikaciiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.service.KvalifikaciiService;

@Service
public class KvalifikaciiServiceImpl implements KvalifikaciiService {

    private final KvalifikaciiJpa kvalifikaciiJpa;
    private final NastavniciJpa nastavniciJpa;
    private final AdminiJpa adminiJpa;

    public KvalifikaciiServiceImpl(KvalifikaciiJpa kvalifikaciiJpa, NastavniciJpa nastavniciJpa, AdminiJpa adminiJpa) {
        this.kvalifikaciiJpa = kvalifikaciiJpa;
        this.nastavniciJpa = nastavniciJpa;
        this.adminiJpa = adminiJpa;
    }

    @Override
    public void insert(String document,Integer idTeacher) {
        Nastavnici n = nastavniciJpa.findById(idTeacher).orElseThrow(TeacherNotFoundException::new);
        Admini a = adminiJpa.findById(1).orElseThrow(AdminNotFoundException::new);
        Kvalifikacii kvalifikacija = new Kvalifikacii();
        kvalifikacija.setDokument(document);
        kvalifikacija.setIdAdmin(a);
        kvalifikacija.setIdNastavnik(n);
        kvalifikaciiJpa.save(kvalifikacija);
    }

}
