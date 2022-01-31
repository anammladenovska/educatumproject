package project.educatum.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.PasswordsDoNotMatchException;
import project.educatum.repository.UceniciJpa;
import project.educatum.service.UceniciService;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UceniciServiceImpl implements UceniciService{

    private final UceniciJpa uceniciRepository;
    private final PasswordEncoder passwordEncoder;

    public UceniciServiceImpl(UceniciJpa uceniciRepository, PasswordEncoder passwordEncoder) {
        this.uceniciRepository = uceniciRepository;
        this.passwordEncoder = passwordEncoder;
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
        //String opis, String ime, String prezime, String email, String password, String telefonskiBroj
        Ucenici user = new Ucenici(opis,ime,prezime,email,passwordEncoder.encode(password),telBroj);
        uceniciRepository.save(user);
    }
}
