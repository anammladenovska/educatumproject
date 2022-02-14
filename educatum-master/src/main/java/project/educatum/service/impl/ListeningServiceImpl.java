package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Listening;
import project.educatum.repository.ListeningRepository;
import project.educatum.service.SlusanjeService;

import java.util.List;

@Service
public class ListeningServiceImpl implements SlusanjeService {
    private final ListeningRepository listeningRepository;

    public ListeningServiceImpl(ListeningRepository listeningRepository) {
        this.listeningRepository = listeningRepository;
    }

    @Override
    public List<Listening> findAll() {
        return listeningRepository.findAll();
    }
}
