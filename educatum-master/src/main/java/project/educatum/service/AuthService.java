package project.educatum.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    UserDetails loginTeacher(String email, String password);

    UserDetails loginStudent(String email, String password);

    UserDetails loginAdmin(String email, String password);
}
