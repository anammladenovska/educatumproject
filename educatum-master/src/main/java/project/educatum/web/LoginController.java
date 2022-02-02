package project.educatum.web;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.Admini;
import project.educatum.model.Nastavnici;
import project.educatum.model.Ucenici;
import project.educatum.model.exceptions.InvalidUserCredentialsException;
import project.educatum.service.AdminiService;
import project.educatum.service.AuthService;
import project.educatum.service.NastavniciService;
import project.educatum.service.UceniciService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {


    private final NastavniciService nastavniciService;
    private final UceniciService uceniciService;
    private final AdminiService adminiService;
    private final AuthService authService;

    public LoginController(NastavniciService nastavniciService, UceniciService uceniciService, AdminiService adminiService, AuthService authService) {
        this.nastavniciService = nastavniciService;
        this.uceniciService = uceniciService;
        this.adminiService = adminiService;
        this.authService = authService;
    }


    @GetMapping
    public String getLoginPage() {
        return "najava";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model, @RequestParam String email, @RequestParam String password) {
        for(Nastavnici n : nastavniciService.findAll()){
            if(n.getEmail().equals(email)){
                try {
                    UserDetails user = authService.loginNastavnik(email,password);
                    request.getSession().setAttribute("user", user);
                    return "redirect:/nastavnici/allStudents";
                } catch (BadCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "/najava";
                }
            }
        }

        for(Ucenici u : uceniciService.findAll()){
            if(u.getEmail().equals(email)){
                try {
                    UserDetails user = authService.loginUcenik(email,password);
                    request.getSession().setAttribute("user", user);
                    return "/nastavnici";
                } catch (InvalidUserCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "/najava";
                }
            }
        }


        for(Admini a : adminiService.findAll()){
            if(a.getEmail().equals(email)){
                try {
                    UserDetails user = authService.loginAdmin(email,password);
                    request.getSession().setAttribute("user", user);
                    return "/nastavnici";
                } catch (InvalidUserCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "/najava";
                }
            }
        }
          return "redirect:/home";
    }
}
