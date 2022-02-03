package project.educatum.service.impl;

import org.springframework.stereotype.Service;
import project.educatum.model.Casovi;
import project.educatum.model.PredavaNa;
import project.educatum.model.Slusanje;
import project.educatum.service.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlakjanjaServiceImpl implements PlakjanjaService {

    private final SlusanjeService slusanjeService;
    private final NastavniciService nastavniciService;
    private final PredavaNaService predavaNaService;
    private final CasoviService casoviService;

    @PersistenceContext
    private EntityManager entityManager;

    public PlakjanjaServiceImpl(SlusanjeService slusanjeService, NastavniciService nastavniciService, PredavaNaService predavaNaService, CasoviService casoviService) {
        this.slusanjeService = slusanjeService;
        this.nastavniciService = nastavniciService;
        this.predavaNaService = predavaNaService;
        this.casoviService = casoviService;
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
                         	(select u.id_ucenik ucenik1, u.ime, count(s.id_cas || '-' || s.id_slusanje) slusaniCasovi
                         from project.slusanje s
                         join project.ucenici u on u.id_ucenik=s.id_ucenik
                         group by 1)q1
                         		
                         		
                         join\s
                         
                         (
                         select c.id_cas,n.id_nastavnik,n.ime,s.id_ucenik ucenik2 from project.casovi c
                         join project.slusanje s on s.id_cas=c.id_cas
                         join project.nastavnici n on n.id_nastavnik=
                         c.id_nastavnik
                         ) q2 on\s
                         q1.ucenik1=q2.ucenik2)q3
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
                        	
                        	
                        	
                        	
                        	select q3.id_nastavnik1, q3.nastavnik1 || ' ' || q3.nastavnik_prezime1 as nastavnik,
                                                 q3.id_ucenik1,q3.ime_ucenik1 as ucenik, (q3.vkupno_za_plakjanje-q3.plateno) as dolzi
                                                 from
                                                 (
                                                         select * from
                                                \s
                                                         (
                                                         select n.id_nastavnik as id_nastavnik1,n.ime as nastavnik1, n.prezime nastavnik_prezime1,
                        									 u.id_ucenik as id_ucenik1,u.ime || ' ' || u.prezime ime_ucenik1,
                                                         sum(pl.iznos) as plateno  from project.ucenici u
                                                         join project.slusanje s on s.id_ucenik = u.id_ucenik
                                                         join project.plakjanja pl on pl.id_plakjanja = s.id_plakjanja
                                                         join project.predava_na pn on pn.id_ucenik = u.id_ucenik
                                                         join project.nastavnici n on n.id_nastavnik =pn.id_nastavnik
                                                         group by 1,2,3,4
                                                         ) q1
                                                \s
                                                         join
                                                \s
                                                         (
                                                         select n.id_nastavnik as id_nastavnik2,n.ime as nastavnik2, n.prezime nastavnik_prezime2,
                        									 u2.id_ucenik as id_ucenik2, u2.ime || ' ' || u2.prezime as ime_ucenik2,
                                                         (pn.cena_po_cas * pn.broj_casovi_po_dogovor) vkupno_za_plakjanje from project.ucenici u2
                                                         join project.predava_na pn on pn.id_ucenik = u2.id_ucenik
                                                         join project.nastavnici n on n.id_nastavnik =pn.id_nastavnik
                                                         ) q2
                                                         on q1.ime_ucenik1 = q2.ime_ucenik2
                                                 ) q3
                        		)q4
                        		group by 1,2,3,4
                        		order by id_nastavnik\s
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
            String idNastavnik = parts[0];
            String idUcenik = parts[3];
            String dolzi = parts[6];
            if (Integer.valueOf(idUcenik).equals(studentId)
                    && Integer.valueOf(idNastavnik).equals(teacherId)) {
                total += Integer.parseInt(dolzi);
            }
        }
        return total;
    }

    @Override
    public Integer brojSlusaniCasovi(Integer idUcenik, Integer idNastavnik) {
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
            String brojCasovi = parts[2];
            if(Integer.valueOf(idU).equals(idUcenik)&&Integer.valueOf(idN).equals(idNastavnik))
                total+=Integer.parseInt(brojCasovi);
        }
        return total;
    }

}
