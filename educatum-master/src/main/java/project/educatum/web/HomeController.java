package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.Admini;
import project.educatum.model.Predmeti;
import project.educatum.service.AdminiService;
import project.educatum.service.PredmetiService;

import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final PredmetiService predmetiService;
    private final AdminiService adminiService;

    public HomeController(PredmetiService predmetiService, AdminiService adminiService) {
        this.predmetiService = predmetiService;
        this.adminiService = adminiService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/izberiPredmet")
    public String choose(@RequestParam String ime, Model model) {
        List<Predmeti> predmeti;
        if (ime == null) {
            predmeti = this.predmetiService.findAll();
        } else {
            predmeti = this.predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("predmeti", predmeti);
        return "izberiPredmeti.html";
    }

    @GetMapping("/izberiPredmet/document")
    public String document() {
        return "prikaziDokument.html";
    }

    @GetMapping("/izberiPredmet/document/potvrda")
    public String potvrdaZaNastavnik() {
        return "potvrdaNastavnik.html";
    }

    @GetMapping("/izberiPredmet/add")
    public String dodadiPredmet(Model model) {
        List<Admini> adminiList = this.adminiService.listAll();
        model.addAttribute("adminiList", adminiList);
        return "dodadiForma.html";
    }

    @PostMapping("/izberiPredmet")
    public String kreirajPredmet(@RequestParam String ime,
                                 @RequestParam List<Integer> idAdmin) {
        this.predmetiService.create(ime, idAdmin);
        return "redirect:/izberiPredmet";
    }

    @GetMapping("/slusajPredmet")
    public String slusajPredmet(String ime, Model model) {
        List<Predmeti> listaPredmeti;
        if (ime == null) {
            listaPredmeti = this.predmetiService.findAll();
        } else {
            listaPredmeti = this.predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("listaPredmeti", listaPredmeti);
        return "slusajPredmet.html";
    }

}
