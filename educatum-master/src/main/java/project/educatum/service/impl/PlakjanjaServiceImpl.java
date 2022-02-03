//package project.educatum.service.impl;
//
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Service;
//import project.educatum.model.Ucenici;
//import project.educatum.service.PlakjanjaService;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Tuple;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class PlakjanjaServiceImpl implements PlakjanjaService {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    public HashMap<Ucenici,Integer> ucenikDolzi(){
//
//        Map<Ucenici, Integer> ucenikZaPlakjanje = em.createNativeQuery(
//                "select q3.ime_ucenik1 as ucenik, (q3.vkupno_za_plakjanje-q3.plateno) as dolzi\n" +
//                        "from\n" +
//                        "(\n" +
//                        "        select * from\n" +
//                        "\n" +
//                        "        (\n" +
//                        "        select n.ime as nastavnik1, n.prezime nastavnik_prezime1, u.ime || ' ' || u.prezime ime_ucenik1, \n" +
//                        "        sum(p.iznos) plateno from ucenici u\n" +
//                        "        join slusanje s on s.id_ucenik = u.id_ucenik \n" +
//                        "        join plakjanja p on p.id_plakjanja = s.id_plakjanja \n" +
//                        "        join predava_na pn on pn.id_ucenik = u.id_ucenik \n" +
//                        "        join nastavnici n on n.id_nastavnik =pn.id_nastavnik\n" +
//                        "        group by 1,2,3\n" +
//                        "        ) q1\n" +
//                        "\n" +
//                        "        join\n" +
//                        "\n" +
//                        "        (\n" +
//                        "        select n.ime as nastavnik2, n.prezime nastavnik_prezime2, u2.ime || ' ' || u2.prezime as ime_ucenik2, \n" +
//                        "        (pn.cena_po_cas * pn.broj_casovi_po_dogovor) vkupno_za_plakjanje from ucenici u2\n" +
//                        "        join predava_na pn on pn.id_ucenik = u2.id_ucenik \n" +
//                        "        join nastavnici n on n.id_nastavnik =pn.id_nastavnik\n" +
//                        "        ) q2\n" +
//                        "        on q1.ime_ucenik1 = q2.ime_ucenik2\n" +
//                        ") q3"
//                , Tuple.class)
//                .getResultStream()
//                .collect(
//                        Collectors.toMap(
//                                tuple -> ((Ucenici) tuple.get("ucenik")),
//                                tuple -> ((Number) tuple.get("dolzi")).intValue()
//                        )
//                );
//
//    }
//
//}
