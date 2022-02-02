package project.educatum.repository;

import org.h2.jdbc.JdbcParameterMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.educatum.model.DomasniZadaci;
@Repository
public interface DomasniZadaciJpa extends JpaRepository<DomasniZadaci,Integer> {
}
