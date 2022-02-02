package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.Slusanje;
import project.educatum.model.SlusanjeId;
@Repository
public interface SlusanjeJpa extends JpaRepository<Slusanje, SlusanjeId> {
}
