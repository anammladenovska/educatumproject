package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Plakjanja;

public interface PlakjanjaJpa extends JpaRepository<Plakjanja,Integer> {
}
