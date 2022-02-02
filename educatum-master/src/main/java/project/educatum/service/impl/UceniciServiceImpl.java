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
import project.educatum.service.UceniciService;

import java.util.List;

@Service
public class UceniciServiceImpl implements UceniciService{

    private final UceniciJpa uceniciRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminiJpa adminiRepository;
    private final NastavniciJpa nastavniciRepository;

    public UceniciServiceImpl(UceniciJpa uceniciRepository, PasswordEncoder passwordEncoder, AdminiJpa adminiRepository, NastavniciJpa nastavniciRepository) {
        this.uceniciRepository = uceniciRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminiRepository = adminiRepository;
        this.nastavniciRepository = nastavniciRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Ucenici user = this.uceniciRepository.findByEmail(email);
//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(),
//                user.getPassword(),
//                Stream.of(new SimpleGrantedAuthority("ROLE_UCENIK")).collect(Collectors.toList())
//        );
//    }

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

        //String opis, String ime, String prezime, String email, String password, String telefonskiBroj
        Ucenici user = new Ucenici(opis,ime,prezime,email,passwordEncoder.encode(password),telBroj);
        uceniciRepository.save(user);
    }

    @Override
    public List<Ucenici> findAll(){
        return uceniciRepository.findAll();
    }

    @Override
    public Ucenici findByEmail(String email) {
        return uceniciRepository.findByEmail(email);
    }
}
