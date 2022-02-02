package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.service.NastavniciService;
import project.educatum.service.PredavaPredmetService;
import project.educatum.service.PredmetiService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/nastavnici")
public class NastavniciController {

    private final NastavniciService nastavniciService;
    private final PredmetiService predmetiService;
    private final PredavaPredmetService predavaPredmetService;

    public NastavniciController(NastavniciService nastavniciService, PredmetiService predmetiService, PredavaPredmetService predavaPredmetService) {
        this.nastavniciService = nastavniciService;
        this.predmetiService = predmetiService;
        this.predavaPredmetService = predavaPredmetService;
    }

    @GetMapping
    public String getAllTeachers(Model model){
        model.addAttribute("teachers",nastavniciService.findAll());
        return "nastavnici";
    }

    @PostMapping("/predavaPredmet")
    public String predavaPredmet(@RequestParam String tema, @RequestParam String predmetId, HttpServletRequest request){
        Optional<Predmeti> p = predmetiService.findById(Integer.valueOf(predmetId));
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
       String username = user.getUsername();
       Nastavnici n = nastavniciService.findByEmail(username);
        predavaPredmetService.addSubject(n.getId(), Integer.valueOf(predmetId),tema);
       return "prikaziDokument";
    }
}
