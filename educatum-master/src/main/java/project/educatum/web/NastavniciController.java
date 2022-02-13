package project.educatum.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Casovi;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/nastavnici", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class NastavniciController {

    private final NastavniciService nastavniciService;
    private final PredmetiService predmetiService;
    private final PredavaPredmetService predavaPredmetService;
    private final UceniciService uceniciService;
    private final PlakjanjaService plakjanjaService;
    private final PredavaNaService predavaNaService;
    private final CasoviService casoviService;

    public NastavniciController(NastavniciService nastavniciService, PredmetiService predmetiService, PredavaPredmetService predavaPredmetService, UceniciService uceniciService, PlakjanjaService plakjanjaService, PredavaNaService predavaNaService, CasoviService casoviService) {
        this.nastavniciService = nastavniciService;
        this.predmetiService = predmetiService;
        this.predavaPredmetService = predavaPredmetService;
        this.uceniciService = uceniciService;
        this.plakjanjaService = plakjanjaService;
        this.predavaNaService = predavaNaService;
        this.casoviService = casoviService;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", nastavniciService.findAll());
        return "nastavnici";
    }

    @GetMapping("/allStudents")
    public String getAllStudentsByTeacher(@RequestParam(required = false) String ime, Model model, HttpServletRequest request) {
        List<Ucenici> ucenici = new ArrayList<>();
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        if (ime == null) {
            ucenici = nastavniciService.getStudentsByTeacher(nastavnik.getId());
        } else {
            ucenici = this.uceniciService.findAllByNameLike(ime, nastavniciService.getStudentsByTeacher(nastavnik.getId()));
        }
        model.addAttribute("nastavnik", nastavniciService.findById(nastavnik.getId()));
        model.addAttribute("ucenici", ucenici);
        return "evidencija";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable String id){
        nastavniciService.delete(Integer.parseInt(id));
        return "redirect:/admini/allTeachers";
    }


    @PostMapping("/payments/{id}")
    public String paymentForStudent(@PathVariable String id, Model model, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        Integer dolzi = plakjanjaService.studentTeacherLoan(Integer.valueOf(id), nastavnik.getId());
        model.addAttribute("dolzi", dolzi);
        Integer brojCasoviPoDogovor = predavaNaService.find(nastavnik.getId(),Integer.valueOf(id)).getBrojCasoviPoDogovor();
        model.addAttribute("brojCasoviPoDogovor",brojCasoviPoDogovor);
        Integer cenaPoCas = predavaNaService.find(nastavnik.getId(),Integer.valueOf(id)).getCenaPoCas();
        model.addAttribute("cenaPoCas",cenaPoCas);
        Integer brojSlusaniCasovi = plakjanjaService.brojSlusaniCasovi(Integer.valueOf(id),nastavnik.getId());
        model.addAttribute("brojSlusaniCasovi",brojSlusaniCasovi);
        model.addAttribute("student",uceniciService.findById(Integer.valueOf(id)));
        return "vnesPlakjanje";
    }

    @PostMapping("/updatePayment")
    public String updatePaymentForStudent(@RequestParam String studentId, Model model, HttpServletRequest request,
                                          @RequestParam String price, @RequestParam String numPaidClasses){








        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        nastavniciService.addPayment(nastavnik.getId(),Integer.valueOf(price));
        Integer dolzi = plakjanjaService.studentTeacherLoan(Integer.valueOf(studentId), nastavnik.getId());
        model.addAttribute("dolzi", dolzi);
        Integer brojCasoviPoDogovor = predavaNaService.find(nastavnik.getId(),Integer.valueOf(studentId)).getBrojCasoviPoDogovor();
        model.addAttribute("brojCasoviPoDogovor",brojCasoviPoDogovor);
        Integer cenaPoCas = predavaNaService.find(nastavnik.getId(),Integer.valueOf(studentId)).getCenaPoCas();
        model.addAttribute("cenaPoCas",cenaPoCas);
        Integer brojSlusaniCasovi = plakjanjaService.brojSlusaniCasovi(Integer.valueOf(studentId),nastavnik.getId());
        model.addAttribute("brojSlusaniCasovi",brojSlusaniCasovi);
        model.addAttribute("student",uceniciService.findById(Integer.valueOf(studentId)));
        return "vnesPlakjanje";
    }


    @GetMapping("/allClasses")
    public String timetable(Model model, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        List<Casovi> casovi = nastavniciService.getClassesByTeacher(nastavnik.getId());
        model.addAttribute("nastavnik", nastavniciService.findById(nastavnik.getId()));
        model.addAttribute("casovi", casovi);
        return "raspored";
    }


    @GetMapping("/allSubjects")
    public String getAllSubjectsByTeacher(@RequestParam(required = false) String ime, Model model, HttpServletRequest request) {
        List<Predmeti> predmeti = new ArrayList<>();
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        if (ime == null) {
            predmeti = nastavniciService.getSubjectsByTeacher(nastavnik.getId());
        } else {
            predmeti = this.predmetiService.findAllByNameAndTeacherLike(ime, nastavniciService.getSubjectsByTeacher(nastavnik.getId()));
        }
        model.addAttribute("nastavnik", nastavniciService.findById(nastavnik.getId()));
        model.addAttribute("predmeti", predmeti);
        return "subjectsByTeacher";
    }


    @PostMapping("/addStudentForm")
    public String addStudentForm() {
        return "addNewStudent";
    }

    @PostMapping("/addSubjectForm")
    public String addSubjectForm(Model model) {
        model.addAttribute("predmeti",predmetiService.findAll());
        return "addNewSubject";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String price, @RequestParam String numClasses,
                             @RequestParam String email, HttpServletRequest request) {
        Ucenici ucenik = uceniciService.findByEmail(email);
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());

        nastavniciService.addStudent(nastavnik.getId(), ucenik.getId(), Integer.valueOf(price), Integer.valueOf(numClasses));
        return "redirect:/nastavnici/allStudents";
    }


    @PostMapping("/addSubject")
    public String addSubject(Model model, HttpServletRequest request,
                             @RequestParam String subjectId,
                             @RequestParam String desc) {

        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        nastavniciService.addSubject(nastavnik.getId(), Integer.valueOf(subjectId),desc);

        return "redirect:/nastavnici/allSubjects";
    }

    @PostMapping("/profile")
    public String showProfile(Model model, HttpServletRequest request){
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        model.addAttribute("ime",nastavnik.getIme()+" "+nastavnik.getPrezime());
        model.addAttribute("opis",nastavnik.getOpis());
        model.addAttribute("email",nastavnik.getEmail());
        model.addAttribute("tel",nastavnik.getTelefonskiBroj());
        return "userInfo";
    }



    @PostMapping("/predavaPredmet")
    public String predavaPredmet(@RequestParam String tema, @RequestParam String predmetId, HttpServletRequest request) {
        Optional<Predmeti> p = predmetiService.findById(Integer.valueOf(predmetId));
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Nastavnici n = nastavniciService.findByEmail(username);
        predavaPredmetService.addSubject(n.getId(), Integer.valueOf(predmetId), tema);
        return "redirect:/home/document";
    }

//OVIE DVA METODI PODOLU DA SE DOVRSAT ZA DODAVANJE NA CHAS

    @PostMapping("/addClassForm")
    public String addClassForm(Model model) {
        model.addAttribute("predmeti",predmetiService.findAll());
        return "addNewClass";
    }

    @PostMapping("/addClass")
    public String addClass(
                           @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
                           @RequestParam String desc,
                           @RequestParam String ime,
                           HttpServletRequest request) {
//        Ucenici ucenik = uceniciService.findByEmail(email);
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        Predmeti predmet = predmetiService.findByName(ime);
//        nastavniciService.addStudent(nastavnik.getId(), ucenik.getId(), Integer.valueOf(price), Integer.valueOf(numClasses));

        casoviService.addClass(date,desc,nastavnik.getId(),predmet.getId());
        return "redirect:/nastavnici/allClasses";
    }

    @PostMapping("/showProfileTeacher/{id}")
    public String showProfileTeacher(@PathVariable String id, Model model){
        Nastavnici nastavnik = nastavniciService.findById(Integer.valueOf(id));
        model.addAttribute("nastavnik",nastavnik);
        return "showProfileTeacher.html";
    }


}
