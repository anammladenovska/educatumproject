package project.educatum.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;
import project.educatum.service.PredmetiService;
import project.educatum.service.UceniciService;
import project.educatum.service.ZainteresiraniZaService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping(path = "/ucenici", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class UceniciController {

    private final UceniciService uceniciService;
    private final PredmetiService predmetiService;
    private final ZainteresiraniZaService zainteresiraniZaService;

    public UceniciController(UceniciService uceniciService, PredmetiService predmetiService, ZainteresiraniZaService zainteresiraniZaService) {
        this.uceniciService = uceniciService;
        this.predmetiService = predmetiService;
        this.zainteresiraniZaService = zainteresiraniZaService;
    }
    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id){
        uceniciService.delete(Integer.parseInt(id));
        return "redirect:/admini/allStudents";
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
