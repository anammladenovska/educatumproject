package project.educatum.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.PasswordsDoNotMatchException;
import project.educatum.repository.NastavniciJpa;
import project.educatum.service.NastavniciService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NastavniciServiceImpl implements NastavniciService {

    private final NastavniciJpa nastavniciRepository;
    private final PasswordEncoder passwordEncoder;

    public NastavniciServiceImpl(NastavniciJpa nastavniciRepository, PasswordEncoder passwordEncoder) {
        this.nastavniciRepository = nastavniciRepository;
        this.passwordEncoder = passwordEncoder;
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
        //String ime, String prezime, String opis, String email, String password, String telefonskiBroj
        Nastavnici user = new Nastavnici(ime,prezime,opis,email,passwordEncoder.encode(password),telBroj);
        nastavniciRepository.save(user);
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
