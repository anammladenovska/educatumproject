package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.educatum.model.Predmeti;
import project.educatum.service.PredmetiService;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final PredmetiService predmetiService;

    public HomeController(PredmetiService predmetiService) {
        this.predmetiService = predmetiService;
    }


    @GetMapping
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/izberiPredmet")
    public String choose(String ime, Model model){
        List<Predmeti> predmeti;
        if(ime == null){
            predmeti = this.predmetiService.findAll();
        }
        else{
            predmeti = this.predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("predmeti",predmeti);
        return "izberiPredmeti.html";
    }

    @GetMapping("/izberiPredmet/document")
    public String document(){
        return "prikaziDokument.html";
    }

    @GetMapping("/izberiPredmet/document/potvrda")
    public String potvrdaZaNastavnik(){
        return "potvrdaNastavnik.html";
    }
}
