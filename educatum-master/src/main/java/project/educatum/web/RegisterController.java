package project.educatum.web;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.exceptions.InvalidArgumentsException;
import project.educatum.model.exceptions.PasswordsDoNotMatchException;
import project.educatum.model.exceptions.UserNotEnabledException;
import project.educatum.model.exceptions.UsernameAlreadyExistsException;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final NastavniciService nastavniciService;
    private final UceniciService uceniciService;
    private final AdminiService adminiService;
    private final PredmetiService predmetiService;
    private final AuthService authService;

    public RegisterController(NastavniciService nastavniciService, UceniciService uceniciService, AdminiService adminiService, PredmetiService predmetiService, AuthService authService) {
        this.nastavniciService = nastavniciService;
        this.uceniciService = uceniciService;
        this.adminiService = adminiService;
        this.predmetiService = predmetiService;
        this.authService = authService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("errors", error);

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
                           @RequestParam(required = false) String opis,
                           HttpServletRequest request) {


        if (role.equals("ROLE_NASTAVNIK")) {
            try {
                this.nastavniciService.register(ime, prezime, email, password, repeatPassword, telBroj, opis);
                UserDetails user = authService.loginNastavnik(email, password);
                request.getSession().setAttribute("user", user);
                return "redirect:/home/izberiPredmet";

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException | UsernameAlreadyExistsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
            catch (UserNotEnabledException e){
                request.getSession().setAttribute("user",nastavniciService.findByEmail(email));
                return "redirect:/home/izberiPredmet";
            }
        } else if (role.equals("ROLE_UCENIK")) {
            try {
                this.uceniciService.register(ime, prezime, email, password, repeatPassword, telBroj, opis);
                UserDetails user = authService.loginUcenik(email, password);
                request.getSession().setAttribute("user", user);
                return "redirect:/home/zainteresiran";

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
        }

        return "redirect:/login";
    }

}

