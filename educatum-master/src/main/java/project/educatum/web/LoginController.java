package project.educatum.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.educatum.model.exceptions.InvalidUserCredentialsException;
import project.educatum.service.NastavniciService;
import project.educatum.service.UceniciService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    //Ana

    private final NastavniciService nastavniciService;
    private final UceniciService uceniciService;

    public LoginController(NastavniciService nastavniciService, UceniciService uceniciService) {
        this.nastavniciService = nastavniciService;
        this.uceniciService = uceniciService;
    }


    @GetMapping
    public String getLoginPage() {
        return "najava";
    }

//    @PostMapping
//    public String login(HttpServletRequest request, Model model) {
//        User user = null;
//        try {
//            user = authService.login(request.getParameter("username"), request.getParameter("password"));
//            request.getSession().setAttribute("user", user);
//            return "redirect:/home";
//        } catch (InvalidUserCredentialsException ex) {
//            model.addAttribute("haserror", true);
//            model.addAttribute("error", ex.getMessage());
//            return "login";
//        }
//    }
}
