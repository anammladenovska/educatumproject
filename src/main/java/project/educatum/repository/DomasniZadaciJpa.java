package project.educatum.repository;

import org.h2.jdbc.JdbcParameterMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import project.educatum.model.DomasniZadaci;

public interface DomasniZadaciJpa extends JpaRepository<DomasniZadaci,Integer> {
}
