package project.educatum.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.*;
import project.educatum.model.exceptions.*;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.PredavaNaJpa;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.NastavniciService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NastavniciServiceImpl implements NastavniciService {

    private final NastavniciJpa nastavniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminiJpa adminiRepository;
    private final UceniciJpa uceniciRepository;
    private final PredavaNaJpa predavaNaJpa;

    public NastavniciServiceImpl(NastavniciJpa nastavniciRepository, PasswordEncoder passwordEncoder, AdminiJpa adminiRepository, UceniciJpa uceniciRepository, PredavaNaJpa predavaNaJpa) {
        this.nastavniciRepository = nastavniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminiRepository = adminiRepository;
        this.uceniciRepository = uceniciRepository;
        this.predavaNaJpa = predavaNaJpa;
    }

    @Override
    public List<Nastavnici> findAll() {
        return nastavniciRepository.findAll();
    }

    @Override
    public void register(String ime, String prezime, String email, String password, String repeatPassword, String telBroj, String opis) {
        if(email==null || email.isEmpty() || password==null || password.isEmpty())
            throw new InvalidArgumentsException();
        if(!password.equals(repeatPassword)) throw new PasswordsDoNotMatchException();

        for(Nastavnici n : nastavniciRepository.findAll()){
            if(n.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for(Ucenici u : uceniciRepository.findAll()){
            if(u.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }
        for(Admini a : adminiRepository.findAll()){
            if(a.getEmail().equals(email)) throw new UsernameAlreadyExistsException("Username already exists!");
        }

        Nastavnici user = new Nastavnici( ime,prezime,opis,email,passwordEncoder.encode(password),telBroj);
        user.setIdAdmin(adminiRepository.findAll().get(0));
        nastavniciRepository.save(user);
    }

    @Override
    public Nastavnici findByEmail(String email) {
        return nastavniciRepository.findByEmail(email);
    }

    @Override
    public List<Ucenici> getStudentsByTeacher(Integer id){
        List<Ucenici> ucenici = new ArrayList<>();
        List<PredavaNa> nastavniciUcenici = predavaNaJpa.findAll();
        for(PredavaNa p : nastavniciUcenici){
            PredavaNaId pId = p.getId();
            if(pId.getIdNastavnik().equals(id)){
                Integer idUcenik = pId.getIdUcenik();
                ucenici.add(uceniciRepository.findById(idUcenik).orElseThrow(StudentNotFoundException::new));
            }
        }
        return ucenici;
    }

    @Override
    public Nastavnici findById(Integer id) {
        return nastavniciRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
    }

    @Override
    public Nastavnici findByEmail(String email){
        return nastavniciRepository.findByEmail(email);
    }

    @Override
    public void addStudent(Integer nastavnikId, Integer ucenikId, Integer cenaPoCas, Integer brojCasoviPoDogovor) {
       Nastavnici nastavnik = nastavniciRepository.findById(nastavnikId).orElseThrow(TeacherNotFoundException::new);
        Ucenici ucenik = uceniciRepository.findById(ucenikId).orElseThrow(StudentNotFoundException::new);

        PredavaNaId pId = new PredavaNaId(nastavnik.getId(),ucenik.getId());
        predavaNaJpa.save(new PredavaNa(pId, cenaPoCas, brojCasoviPoDogovor));
    }

}
