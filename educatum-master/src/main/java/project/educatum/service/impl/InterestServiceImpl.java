package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Interest;
import project.educatum.model.InterestID;
import project.educatum.repository.InterestRepository;
import project.educatum.service.InterestService;

import java.time.LocalDate;

@Service
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    public InterestServiceImpl(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public void addSubjectStudent(Integer subjectID, Integer ucenikId, LocalDate datum) {

        if(subjectID==null){

        }
        if(ucenikId==null){

        }

        InterestID interestID = new InterestID(subjectID,ucenikId);
        Interest interested = new Interest(interestID,datum);
        interestRepository.save(interested);
    }
}
