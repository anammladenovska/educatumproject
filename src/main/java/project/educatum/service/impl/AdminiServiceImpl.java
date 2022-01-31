package project.educatum.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.repository.AdminiJpa;
import project.educatum.repository.NastavniciJpa;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.AdminiService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminiServiceImpl implements AdminiService, UserDetailsService {

    private final AdminiJpa adminiRepository;
    private final NastavniciJpa nastavniciRepository;
    private final UceniciJpa uceniciRepository;

    public AdminiServiceImpl(AdminiJpa adminiRepository, NastavniciJpa nastavniciRepository, UceniciJpa uceniciRepository) {
        this.adminiRepository = adminiRepository;
        this.nastavniciRepository = nastavniciRepository;
        this.uceniciRepository = uceniciRepository;
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
