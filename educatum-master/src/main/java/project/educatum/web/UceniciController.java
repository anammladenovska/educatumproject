package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Ucenici;
import project.educatum.service.NastavniciService;
import project.educatum.service.PredmetiService;
import project.educatum.service.UceniciService;
import project.educatum.service.ZainteresiraniZaService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping(path = "/ucenici", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class UceniciController {

    private final UceniciService uceniciService;
    private final PredmetiService predmetiService;
    private final ZainteresiraniZaService zainteresiraniZaService;
    private final NastavniciService nastavniciService;

    public UceniciController(UceniciService uceniciService, PredmetiService predmetiService, ZainteresiraniZaService zainteresiraniZaService, NastavniciService nastavniciService) {
        this.uceniciService = uceniciService;
        this.predmetiService = predmetiService;
        this.zainteresiraniZaService = zainteresiraniZaService;
        this.nastavniciService = nastavniciService;
    }

    @GetMapping("/listSubjectsTeachers")
    public String listSubjectsTeachers(Model model){
//        model.addAttribute("predmeti", predmetiService.findAll());
//        model.addAttribute("nastavnici", nastavniciService.getAllTeachersBySubject(Integer.valueOf(subject)));
        return "listSubTeachStudents";
    }

    @PostMapping("/teachers")
    public String getAllTeachersBySubject(Model model, @RequestParam String predmetId) {
        model.addAttribute("predmet", predmetiService.findById(Integer.valueOf(predmetId)));
        model.addAttribute("predmeti", predmetiService.findAll());
        model.addAttribute("nastavnici", nastavniciService.getAllTeachersBySubject(Integer.valueOf(predmetId)));
        return "listSubTeachStudents";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id){
        uceniciService.delete(Integer.parseInt(id));
        return "redirect:/admini/allStudents";
    }

    @PostMapping("/slusanje")
    public String slusanje(Model model, HttpServletRequest request){
        return "";
    }

    @PostMapping("/zaintesesiranZaPredmet")
    public String zaintesesiranZaPredmet(@RequestParam String predmetId,
                                         HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        LocalDate localDate = LocalDate.now();
        String username = user.getUsername();
        Ucenici u = uceniciService.findByEmail(username);
        zainteresiraniZaService.addSubjectStudent(Integer.valueOf(predmetId),u.getId(),localDate );
        return "redirect:/zaintesesiranZaPredmet";
    }


}
