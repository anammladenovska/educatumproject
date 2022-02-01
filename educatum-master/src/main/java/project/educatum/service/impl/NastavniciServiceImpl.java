package project.educatum.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.PasswordsDoNotMatchException;
import project.educatum.model.exceptions.UsernameAlreadyExistsException;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.NastavniciService;

import java.util.List;

@Service
public class NastavniciServiceImpl implements NastavniciService {

    private final NastavniciJpa nastavniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminiJpa adminiRepository;
    private final UceniciJpa uceniciRepository;

    public NastavniciServiceImpl(NastavniciJpa nastavniciRepository, PasswordEncoder passwordEncoder, AdminiJpa adminiRepository, UceniciJpa uceniciRepository) {
        this.nastavniciRepository = nastavniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminiRepository = adminiRepository;
        this.uceniciRepository = uceniciRepository;
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

        //String ime, String prezime, String opis, String email, String password, String telefonskiBroj
        Nastavnici user = new Nastavnici( ime,prezime,opis,email,passwordEncoder.encode(password),telBroj);
        user.setIdAdmin(adminiRepository.findAll().get(0));
        nastavniciRepository.save(user);
    }

    @Override
    public Nastavnici findByEmail(String email) {
        return nastavniciRepository.findByEmail(email);
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Nastavnici user = this.nastavniciRepository.findByEmail(email);
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                Stream.of(new SimpleGrantedAuthority("ROLE_NASTAVNIK")).collect(Collectors.toList())
//        );
//
//    }
}
