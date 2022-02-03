package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Casovi;
import project.educatum.repository.CasoviJpa;
import project.educatum.service.CasoviService;

import java.util.List;

@Service
public class CasoviServiceImpl implements CasoviService {

    private final CasoviJpa casoviJpa;

    public CasoviServiceImpl(CasoviJpa casoviJpa) {
        this.casoviJpa = casoviJpa;
    }

    @Override
    public List<Casovi> findAll() {
        return casoviJpa.findAll();
    }

    @Override
    public List<Casovi> findAllByNastavnik(Integer id) {
        return casoviJpa.findAllByIdNastavnik(id);
    }


}
