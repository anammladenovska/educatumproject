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

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AdminService adminService;
    private final SubjectService subjectService;
    private final AuthService authService;

    public RegisterController(TeacherService teacherService, StudentService studentService, AdminService adminService, SubjectService subjectService, AuthService authService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.adminService = adminService;
        this.subjectService = subjectService;
        this.authService = authService;
    }


    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasErrors", true);
            model.addAttribute("errors", error);

        }
        return "register";
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
                this.teacherService.register(ime, prezime, email, password, repeatPassword, telBroj, opis);
                UserDetails user = authService.loginTeacher(email, password);
                request.getSession().setAttribute("user", user);
                return "redirect:/home/chooseSubject";

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException | UsernameAlreadyExistsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
            catch (UserNotEnabledException e){
                request.getSession().setAttribute("user", teacherService.findByEmail(email));
                return "redirect:/home/chooseSubject";
            }
        } else if (role.equals("ROLE_UCENIK")) {
            try {
                this.studentService.register(ime, prezime, email, password, repeatPassword, telBroj, opis);
                UserDetails user = authService.loginStudent(email, password);
                request.getSession().setAttribute("user", user);
                return "redirect:/home/zainteresiran";

            } catch (PasswordsDoNotMatchException | InvalidArgumentsException exception) {
                return "redirect:/register?error=" + exception.getMessage();

            }
        }

        return "redirect:/login";
    }

}

