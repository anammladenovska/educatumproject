package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.ZainteresiranZa;
import project.educatum.model.ZainteresiranZaId;
@Repository
public interface ZainteresiranZaJpa extends JpaRepository<ZainteresiranZa, ZainteresiranZaId> {
}
