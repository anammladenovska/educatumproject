package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Admini;

public interface AdminiJpa extends JpaRepository<Admini,Integer> {
    Admini findByEmail(String email);
    Admini findByEmailAndPassword(String email, String password);
}
