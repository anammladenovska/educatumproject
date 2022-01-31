package project.educatum.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import project.educatum.model.Nastavnici;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface NastavniciJpa extends JpaRepository<Nastavnici, Integer> {
    Nastavnici findByEmail(String email);
}
