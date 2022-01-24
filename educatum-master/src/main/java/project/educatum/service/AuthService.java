package project.educatum.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;

public interface AuthService extends UserDetailsService {
    Nastavnici loginNastavnik(String email, String password);
    Ucenici loginUcenik(String email, String password);
    Admini loginAdmin(String email, String password);
}
