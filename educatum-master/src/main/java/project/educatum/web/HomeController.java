package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Predmeti;
import project.educatum.service.AdminiService;
import project.educatum.service.KvalifikaciiService;
import project.educatum.service.NastavniciService;
import project.educatum.service.PredmetiService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final PredmetiService predmetiService;
    private final AdminiService adminiService;
    private final NastavniciService nastavniciService;
    private final KvalifikaciiService kvalifikaciiService;
//    private static String UPLOADED_FOLDER = "C://Users//Acer//Desktop//dok//";
private static String UPLOADED_FOLDER = "C://Users//User//OneDrive//Desktop//kvalifikacii//";
    public HomeController(PredmetiService predmetiService, AdminiService adminiService, NastavniciService nastavniciService, KvalifikaciiService kvalifikaciiService) {
        this.predmetiService = predmetiService;
        this.adminiService = adminiService;
        this.nastavniciService = nastavniciService;
        this.kvalifikaciiService = kvalifikaciiService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/izberiPredmet")
    public String choose(@RequestParam(required = false) String ime, Model model) {
        List<Predmeti> predmeti;
        if (ime == null) {
            predmeti = this.predmetiService.findAll();
        } else {
            predmeti = this.predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("predmeti", predmeti);
        return "izberiPredmeti.html";
    }

    @GetMapping("/document")
    public String document() {
        return "prikaziDokument.html";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Внесете задолжително документ");
            return "redirect:/home/potvrda";
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            UserDetails userDetails = (UserDetails) request.getSession().getAttribute("user");
            Nastavnici n = nastavniciService.findByEmail(userDetails.getUsername());
            kvalifikaciiService.insert(String.valueOf(path),n.getId());
            redirectAttributes.addFlashAttribute("message",
                    "Ви благодариме за регистрацијата! Ќе добиете известување на e-mail кога вашиот профил ќе виде активиран.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home/potvrda";
    }

    @GetMapping("/potvrda")
    public String potvrdaZaNastavnik() {
        return "potvrdaNastavnik.html";
    }

    @GetMapping("/add")
    public String dodadiPredmet(Model model) {
        List<Admini> adminiList = this.adminiService.listAll();
        model.addAttribute("adminiList", adminiList);
        return "dodadiForma.html";
    }


    @PostMapping("/izberiPredmet")
    public String kreirajPredmet(@RequestParam String ime) {
        this.predmetiService.create(ime);
        return "redirect:/home/izberiPredmet";
    }

    @GetMapping("/slusajPredmet")
    public String slusajPredmet(@RequestParam(required = false)String ime, Model model) {
        List<Predmeti> listaPredmeti;
        if (ime == null) {
            listaPredmeti = this.predmetiService.findAll();
        } else {
            listaPredmeti = this.predmetiService.findAllByNameLike(ime);
        }
        model.addAttribute("listaPredmeti", listaPredmeti);
        model.addAttribute("nastavnici",nastavniciService.findAll());
        return "slusajPredmet";
    }

    @PostMapping("/vidiPredmeti")
    public String getAllTeachersBySubject(Model model, @RequestParam String predmetId){
        model.addAttribute("predmet",predmetiService.findById(Integer.valueOf(predmetId)));
        model.addAttribute("predmeti",predmetiService.findAll());
        model.addAttribute("nastavnici",nastavniciService.getAllTeachersBySubject(Integer.valueOf(predmetId)));
        return "slusajPredmet";
    }

}
