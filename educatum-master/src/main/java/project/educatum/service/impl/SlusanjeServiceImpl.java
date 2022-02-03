package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Slusanje;
import project.educatum.repository.SlusanjeJpa;
import project.educatum.service.SlusanjeService;

import java.util.List;

@Service
public class SlusanjeServiceImpl implements SlusanjeService {
    private final SlusanjeJpa slusanjeJpa;

    public SlusanjeServiceImpl(SlusanjeJpa slusanjeJpa) {
        this.slusanjeJpa = slusanjeJpa;
    }

    @Override
    public List<Slusanje> findAll() {
        return slusanjeJpa.findAll();
    }
}
