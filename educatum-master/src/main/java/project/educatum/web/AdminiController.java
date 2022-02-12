package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.model.Ucenici;
import project.educatum.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admini", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class AdminiController {
    private final NastavniciService nastavniciService;
    private final PredmetiService predmetiService;
    private final PredavaPredmetService predavaPredmetService;
    private final UceniciService uceniciService;
    private final PlakjanjaService plakjanjaService;
    private final PredavaNaService predavaNaService;

    public AdminiController(NastavniciService nastavniciService, PredmetiService predmetiService, PredavaPredmetService predavaPredmetService, UceniciService uceniciService, PlakjanjaService plakjanjaService, PredavaNaService predavaNaService) {
        this.nastavniciService = nastavniciService;
        this.predmetiService = predmetiService;
        this.predavaPredmetService = predavaPredmetService;
        this.uceniciService = uceniciService;
        this.plakjanjaService = plakjanjaService;
        this.predavaNaService = predavaNaService;
    }

    @GetMapping("/allTeachers")
    public String getAllTeachers(@RequestParam(required = false) String ime, Model model){
        List<Nastavnici> teachers = new ArrayList<>();
        if (ime == null) {
            teachers = nastavniciService.findAll();
        } else {
            teachers = nastavniciService.findAllByNameLike(ime);
             }

        model.addAttribute("teachers", teachers);
        return "nastavniciAdmin";
    }

    @GetMapping("/allSubjects")
    public String getAllSubjects(@RequestParam(required = false) String ime,Model model){
        List<Predmeti> subjects = new ArrayList<>();
        if (ime == null) {
            subjects = predmetiService.findAll();
        } else {
            subjects = predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("subjects",subjects);
        return "predmetiAdmin";
    }

    @GetMapping("/allStudents")
    public String getAllStudents(@RequestParam(required = false) String ime,Model model){
        List<Ucenici> students = new ArrayList<>();
        if (ime == null) {
            students = uceniciService.findAll();
        } else {
            students = uceniciService.findAllByName(ime);
        }
        model.addAttribute("students", students);
        return "uceniciAdmin";
    }

    @PostMapping("/activate/{id}")
    public String activateAccount(@PathVariable String id){
        Nastavnici n = nastavniciService.findById(Integer.valueOf(id));
        if(n!=null){
            nastavniciService.updateEnabled(Integer.valueOf(id));
        }
        return "redirect:/admini/allTeachers";
    }


}
