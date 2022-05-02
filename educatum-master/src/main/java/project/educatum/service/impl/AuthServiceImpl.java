package project.educatum.service.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.educatum.model.Admin;
import project.educatum.model.Student;
import project.educatum.model.Teacher;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.UserNotEnabledException;
import project.educatum.repository.AdminRepository;
import project.educatum.repository.TeacherRepository;
import project.educatum.repository.StudentRepository;
import project.educatum.service.AuthService;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    private final TeacherRepository teachersRepository;
    private final AdminRepository adminRepository;
    private final StudentRepository studentsRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(TeacherRepository teachersRepository, AdminRepository adminRepository, StudentRepository studentsRepository, PasswordEncoder passwordEncoder) {
        this.teachersRepository = teachersRepository;
        this.adminRepository = adminRepository;
        this.studentsRepository = studentsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loginTeacher(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        Teacher n = teachersRepository.findByEmail(email);
        if (n != null && n.getEnabled() != null && n.isEnabled()) {
            if (!passwordEncoder.matches(password, n.getPassword())) {
                throw new BadCredentialsException("Invalid credentials");
            }
            UserDetails user = loadUserByUsername(email);
            return user;
        } else throw new UserNotEnabledException();
    }

    @Override
    public UserDetails loginStudent(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        Student u = studentsRepository.findByEmail(email);
        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new BadCredentialsException("Passwords do not match!");
        }
        UserDetails user = loadUserByUsername(email);
        return user;
    }


    @Override
    public UserDetails loginAdmin(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty())
            throw new InvalidArgumentsException();

        UserDetails user = loadUserByUsername(email);

        if (!Objects.equals(user.getPassword(), passwordEncoder.encode(password)))
            throw new BadCredentialsException("Incorrect password!");

        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin userAdmin = this.adminRepository.findByEmail(email);
        Teacher userTeacher = this.teachersRepository.findByEmail(email);
        Student userStudent = this.studentsRepository.findByEmail(email);

        if (userAdmin != null) {
            return new org.springframework.security.core.userdetails.User(
                    userAdmin.getEmail(),
                    userAdmin.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_ADMIN")).collect(Collectors.toList())
            );
        } else if (userTeacher != null) {
            return new org.springframework.security.core.userdetails.User(
                    userTeacher.getEmail(),
                    userTeacher.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_NASTAVNIK")).collect(Collectors.toList())
            );
        } else if (userStudent != null) {
            return new org.springframework.security.core.userdetails.User(
                    userStudent.getEmail(),
                    userStudent.getPassword(),
                    Stream.of(new SimpleGrantedAuthority("ROLE_UCENIK")).collect(Collectors.toList())
            );
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}