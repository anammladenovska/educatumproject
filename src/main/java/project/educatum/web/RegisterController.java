package project.educatum.web;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.PasswordsDoNotMatchException;
import project.educatum.service.NastavniciService;
import project.educatum.service.UceniciService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final NastavniciService nastavniciService;
    private final UceniciService uceniciService;

    public RegisterController(NastavniciService nastavniciService, UceniciService uceniciService) {
        this.nastavniciService = nastavniciService;
        this.uceniciService = uceniciService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error!=null && !error.isEmpty()){
            model.addAttribute("hasErrors",true);
            model.addAttribute("errors",error);

        }
        return "registracija";
    }

    @PostMapping("/registrirajSe")
    public String register(@RequestParam String ime,
                           @RequestParam String prezime,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           @RequestParam String role,
                           @RequestParam String telBroj,
                           @RequestParam(required = false) String opis) {
        if(role.equals("ROLE_NASTAVNIK")){
            try {
                this.nastavniciService.register(ime, prezime,email,password,repeatPassword,telBroj,opis);

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
        }
        else if(role.equals("ROLE_UCENIK")){
            try {
                this.uceniciService.register(ime, prezime,email,password,repeatPassword,telBroj,opis);

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
        }

        return "redirect:/login";
    }
}

