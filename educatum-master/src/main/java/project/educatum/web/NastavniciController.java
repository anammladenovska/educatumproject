package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Casovi;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;
import project.educatum.service.NastavniciService;
import project.educatum.service.PredavaPredmetService;
import project.educatum.service.PredmetiService;
import project.educatum.service.UceniciService;

import javax.servlet.http.HttpServletRequest;
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

    public NastavniciController(NastavniciService nastavniciService, PredmetiService predmetiService, PredavaPredmetService predavaPredmetService, UceniciService uceniciService) {
        this.nastavniciService = nastavniciService;
        this.predmetiService = predmetiService;
        this.predavaPredmetService = predavaPredmetService;
        this.uceniciService = uceniciService;
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

    @GetMapping("/allClasses")
    public String timetable(Model model, HttpServletRequest request){
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());
        List<Casovi> casovi = nastavniciService.getClassesByTeacher(nastavnik.getId());
        model.addAttribute("nastavnik", nastavniciService.findById(nastavnik.getId()));
        model.addAttribute("casovi",casovi);
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
        model.addAttribute("predmeti", nastavniciService.getSubjectsByTeacher(nastavnik.getId()));
        return "subjectsByTeacher";
    }


    @PostMapping("/addStudentForm")
    public String addStudentForm() {
        return "addNewStudent";
    }

    @PostMapping("/addStudent")
    public String addStudent(Model model, @RequestParam String price, @RequestParam String numClasses,
                             @RequestParam String email, HttpServletRequest request) {
        Ucenici ucenik = uceniciService.findByEmail(email);
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Nastavnici nastavnik = nastavniciService.findByEmail(user.getUsername());

        nastavniciService.addStudent(nastavnik.getId(), ucenik.getId(), Integer.valueOf(price), Integer.valueOf(numClasses));
        return "redirect:/nastavnici/allStudents";
    }


    @PostMapping("/predavaPredmet")
    public String predavaPredmet(@RequestParam String tema, @RequestParam String predmetId, HttpServletRequest request) {
        Optional<Predmeti> p = predmetiService.findById(Integer.valueOf(predmetId));
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Nastavnici n = nastavniciService.findByEmail(username);
        predavaPredmetService.addSubject(n.getId(), Integer.valueOf(predmetId), tema);
        return "prikaziDokument";
    }


}
