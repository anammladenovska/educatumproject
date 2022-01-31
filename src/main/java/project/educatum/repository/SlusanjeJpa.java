package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.Slusanje;
import project.educatum.model.SlusanjeId;

public interface SlusanjeJpa extends JpaRepository<Slusanje, SlusanjeId> {
}
