package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Casovi;

import java.util.List;

@Repository
public interface CasoviJpa extends JpaRepository<Casovi,Integer> {
    @Query(value = "select * from project.casovi c where c.id_nastavnik = :idNastavnik",
            nativeQuery = true)
    List<Casovi> findAllByIdNastavnik(Integer idNastavnik);
}
