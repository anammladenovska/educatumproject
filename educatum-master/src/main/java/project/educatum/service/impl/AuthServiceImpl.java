package project.educatum.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.InvalidUserCredentialsException;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.AuthService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final NastavniciJpa nastavniciRepository;
    private final AdminiJpa adminiRepository;
    private final UceniciJpa uceniciRepository;

    public AuthServiceImpl(NastavniciJpa nastavniciRepository, AdminiJpa adminiRepository, UceniciJpa uceniciRepository) {
        this.nastavniciRepository = nastavniciRepository;
        this.adminiRepository = adminiRepository;
        this.uceniciRepository = uceniciRepository;
    }

    @Override
    public Nastavnici loginNastavnik(String email, String password) {
        if (email==null || email.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return nastavniciRepository.findByEmailAndPassword(email,
                password);
    }
    @Override
    public Ucenici loginUcenik(String email, String password) {
        if (email==null || email.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return uceniciRepository.findByEmailAndPassword(email,
                password);
    }



    @Override
    public Admini loginAdmin(String email, String password) {
        if (email==null || email.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return adminiRepository.findByEmailAndPassword(email,
                password);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admini userAdmin = this.adminiRepository.findByEmail(email);
        Nastavnici userNastavnik = this.nastavniciRepository.findByEmail(email);
        Ucenici userUcenik = this.uceniciRepository.findByEmail(email);
        if(userAdmin!=null){
            return new org.springframework.security.core.userdetails.User(
                    userAdmin.getEmail(),
                    userAdmin.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList())
            );
        }
        else if(userNastavnik!=null){
            return new org.springframework.security.core.userdetails.User(
                    userNastavnik.getEmail(),
                    userNastavnik.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_NASTAVNIK")).collect(Collectors.toList())
            );
        }
        else if(userUcenik!=null){
            return new org.springframework.security.core.userdetails.User(
                    userUcenik.getEmail(),
                    userUcenik.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_UCENIK")).collect(Collectors.toList())
            );
        }
        else{
            throw new UsernameNotFoundException(email);
        }
    }
}
