package project.educatum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.educatum.model.Class;
import project.educatum.model.Payment;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {

    @Query(value = "select * from project.plakjanja p where p.id_nastavnik = :idTeacher",
            nativeQuery = true)
    List<Payment> findAllByIdTeacher(Integer idTeacher);

    @Transactional
    @Modifying
    @Query("UPDATE Payment p SET p.iznos=:price WHERE p.id=:id")
    void updatePrice(Integer price, Integer id);

}
