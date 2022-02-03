package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.educatum.service.PredmetiService;
import project.educatum.service.UceniciService;
import project.educatum.service.ZainteresiraniZaService;

@Controller
@RequestMapping(path = "/predmeti", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})

public class PredmetiController {


    private final UceniciService uceniciService;
    private final PredmetiService predmetiService;
    private final ZainteresiraniZaService zainteresiraniZaService;

    public PredmetiController(UceniciService uceniciService, PredmetiService predmetiService, ZainteresiraniZaService zainteresiraniZaService) {
        this.uceniciService = uceniciService;
        this.predmetiService = predmetiService;
        this.zainteresiraniZaService = zainteresiraniZaService;
    }


    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable String id){
        predmetiService.delete(Integer.parseInt(id));
        return "redirect:/admini/allSubjects";
    }
}
