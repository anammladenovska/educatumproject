package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.service.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final SlusanjeService slusanjeService;
    private final TeacherService teacherService;
    private final TeacherStudentService teacherStudentService;
    private final ClassService classService;

    @PersistenceContext
    private EntityManager entityManager;

    public PaymentServiceImpl(SlusanjeService slusanjeService, TeacherService teacherService, TeacherStudentService teacherStudentService, ClassService classService) {
        this.slusanjeService = slusanjeService;
        this.teacherService = teacherService;
        this.teacherStudentService = teacherStudentService;
        this.classService = classService;
    }

    @Override
    public List<Object[]> getListenedClassesQuery() {
        javax.persistence.Query q = entityManager.createNativeQuery(
                """
                              	select distinct q3.ucenik1 id_ucenik, q3.id_nastavnik id_nastavnik,
                                              q3.slusanicasovi slusani_casovi
                                              from
                                              (		
                                              	select * from
                                              	(
                                              	
                                                 	select u.id_ucenik ucenik1, u.ime, n.id_nastavnik nastavnik1, count(s.id_cas || '-' || s.id_slusanje) slusanicasovi
                                                 	from project.ucenici u\s
                                                 	join project.slusanje s on s.id_ucenik = u.id_ucenik\s
                                                 	join project.casovi c on c.id_cas = s.id_cas\s
                                                 	join project.nastavnici n on n.id_nastavnik = c.id_nastavnik\s
                                                 	group by 1,2,3
                                             \s
                                             \s
                                             \s
                                              )q1
                                              		
                                              		
                                              join
                                             \s
                                              (
                                              select c.id_cas,n.id_nastavnik as id_nastavnik,n.ime,s.id_ucenik ucenik2, s.id_slusanje id_slusanje2 from project.casovi c
                                              join project.slusanje s on s.id_cas=c.id_cas
                                              join project.nastavnici n on n.id_nastavnik=c.id_nastavnik
                                              ) q2 on
                                              q1.ucenik1=q2.ucenik2
                                              and q1.nastavnik1=q2.id_nastavnik)q3
                        """
        );
        return q.getResultList();
    }


    @Override
    public List<Object[]> getPaymentsQuery() {
        javax.persistence.Query q = entityManager.createNativeQuery(
                """
select q4.id_nastavnik1 as id_nastavnik,q4.nastavnik,\s
        q4.id_ucenik1 as id_ucenik, q4.ucenik as ime_ucenik, sum(q4.dolzi) as dolzi from (
       \s
       \s
       \s
       \s
        select q3.id_nastavnik1, q3.nastavnik1 || ' ' || q3.nastavnik_prezime1 as nastavnik,
                         q3.id_ucenik1,q3.ime_ucenik1 as ucenik, (q3.vkupno_za_plakjanje-q3.plateno) as dolzi
                         from
                         (
                                 select * from
                        \s
                                 (
                                 select n.id_nastavnik as id_nastavnik1,n.ime as nastavnik1, n.prezime nastavnik_prezime1,
                                                                         u.id_ucenik as id_ucenik1,u.ime || ' ' || u.prezime ime_ucenik1,
                                 sum(pl.iznos) as plateno  from
                                 project.nastavnici n\s
                                 join project.plakjanja pl on pl.id_nastavnik = n.id_nastavnik\s
                                 join project.casovi c on c.id_nastavnik = pl.id_nastavnik\s
                                 join project.slusanje s on s.id_cas = c.id_cas\s
                                 and s.id_plakjanja = pl.id_plakjanja\s
                                 join project.ucenici u on u.id_ucenik = s.id_ucenik\s
                                 group by 1,2,3,4
              \s
                                 ) q1
                        \s
                                 join
                         				
                                 (
                                 select n.id_nastavnik as id_nastavnik2,n.ime as nastavnik2, n.prezime nastavnik_prezime2,
                                                                         u2.id_ucenik as id_ucenik2, u2.ime || ' ' || u2.prezime as ime_ucenik2,
                                 (pn.cena_po_cas * pn.broj_casovi_po_dogovor) vkupno_za_plakjanje from project.ucenici u2
                                 join project.predava_na pn on pn.id_ucenik = u2.id_ucenik
                                 join project.nastavnici n on n.id_nastavnik =pn.id_nastavnik
                                 ) q2
                                 on q1.id_ucenik1 = q2.id_ucenik2 and q2.id_nastavnik2=q1.id_nastavnik1
                         ) q3
                )q4
                group by 1,2,3,4
                order by id_nastavnik\s

               \s
               \s
               \s
""");

        return q.getResultList();
    }


    @Override
    public Integer studentTeacherLoan(Integer studentId, Integer teacherId) {
        List<Object[]> query = getPaymentsQuery();
        List<String> payments = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int total = 0;
        for (Object[] payment : query) {
            sb.setLength(0);
            for (int i = 0; i < payment.length; i++) {
                String attribute = payment[i].toString();
                sb.append(attribute).append(" ");
            }
            payments.add(sb.toString());
        }
        for (String p : payments) {
            String[] parts = p.split("\\s+");
            String idTeacher = parts[0];
            String studentID = parts[3];
            String owes = parts[6];
            if (Integer.valueOf(studentID).equals(studentId)
                    && Integer.valueOf(idTeacher).equals(teacherId)) {
                total += Integer.parseInt(owes);
            }
        }
        return total;
    }

    @Override
    public Integer numListenedClasses(Integer idStudent, Integer idTeacher) {
        int total=0;
        List<Object[]> query = getListenedClassesQuery();
        List<String> slusanja = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(Object[] slusanje : query){
            sb.setLength(0);
            for (int i = 0; i < slusanje.length; i++) {
                String attribute = slusanje[i].toString();
                sb.append(attribute).append(" ");
            }
            slusanja.add(sb.toString());
        }
        for(String s : slusanja){
            String[] parts = s.split("\\s+");
            String idU = parts[0];
            String idN =parts[1];
            String numClasses = parts[2];
            if(Integer.valueOf(idU).equals(idStudent)&&Integer.valueOf(idN).equals(idTeacher))
                total+=Integer.parseInt(numClasses);
        }
        return total;
    }

}
