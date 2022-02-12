package project.educatum.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.UserNotEnabledException;
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
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(NastavniciJpa nastavniciRepository, AdminiJpa adminiRepository, UceniciJpa uceniciRepository, PasswordEncoder passwordEncoder) {
        this.nastavniciRepository = nastavniciRepository;
        this.adminiRepository = adminiRepository;
        this.uceniciRepository = uceniciRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loginNastavnik(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        Nastavnici n = nastavniciRepository.findByEmail(email);
        if(n!=null && n.getEnabled()!=null && n.isEnabled()){
            if (!passwordEncoder.matches(password, n.getPassword())) {
                throw new BadCredentialsException("Invalid credentials");
            }
            UserDetails user = loadUserByUsername(email);
            return user;
        }
       else throw new UserNotEnabledException();
    }

    @Override
    public UserDetails loginUcenik(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        Ucenici u = uceniciRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        UserDetails user = loadUserByUsername(email);
        return user;
    }


    @Override
    public UserDetails loginAdmin(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        UserDetails user = loadUserByUsername(email);
        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admini userAdmin = this.adminiRepository.findByEmail(email);
        Nastavnici userNastavnik = this.nastavniciRepository.findByEmail(email);
        Ucenici userUcenik = this.uceniciRepository.findByEmail(email);
        if (userAdmin != null) {
            return new org.springframework.security.core.userdetails.User(
                    userAdmin.getEmail(),
                    userAdmin.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList())
            );
        } else if (userNastavnik != null) {
            return new org.springframework.security.core.userdetails.User(
                    userNastavnik.getEmail(),
                    userNastavnik.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_NASTAVNIK")).collect(Collectors.toList())
            );
        } else if (userUcenik != null) {
            return new org.springframework.security.core.userdetails.User(
                    userUcenik.getEmail(),
                    userUcenik.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_UCENIK")).collect(Collectors.toList())
            );
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
