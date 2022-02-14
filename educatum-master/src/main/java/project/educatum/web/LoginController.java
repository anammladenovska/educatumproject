package project.educatum.web;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.educatum.model.Admin;
import project.educatum.model.Teacher;
import project.educatum.model.Student;
import project.educatum.model.exceptions.InvalidUserCredentialsException;
import project.educatum.model.exceptions.UserNotEnabledException;
import project.educatum.service.AdminService;
import project.educatum.service.AuthService;
import project.educatum.service.TeacherService;
import project.educatum.service.StudentService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {


    private final TeacherService teacherService;
    private final StudentService studentService;
    private final AdminService adminService;
    private final AuthService authService;

    public LoginController(TeacherService teacherService, StudentService studentService, AdminService adminService, AuthService authService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.adminService = adminService;
        this.authService = authService;
    }


    @GetMapping
    public String getLoginPage() {
        return "login";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model, @RequestParam String username, @RequestParam String password) {
        String email = username;

        for (Teacher n : teacherService.findAll()) {
            if (n.getEmail().equals(email)) {
                try {
                    UserDetails user = authService.loginTeacher(email, password);
                    request.getSession().setAttribute("user", user);
                    return "redirect:/teachers/allStudents";
                } catch (BadCredentialsException | InvalidUserCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "login";
                } catch (UserNotEnabledException ex){
                    return "notEnabled";
                }
            }
        }

        for (Student u : studentService.findAll()) {
            if (u.getEmail().equals(email)) {
                try {
                    UserDetails user = authService.loginStudent(email, password);
                    request.getSession().setAttribute("user", user);
                    return "redirect:/students/listSubjectsTeachers?subjectID=1";
                } catch (InvalidUserCredentialsException | BadCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "login";
                }
            }
        }


        for (Admin a : adminService.findAll()) {
            if (a.getEmail().equals(email)) {
                try {
                    UserDetails user = authService.loginAdmin(email, password);
                    request.getSession().setAttribute("user", user);
                    return "redirect:/admin/allTeachers";
                } catch (InvalidUserCredentialsException | BadCredentialsException ex) {
                    model.addAttribute("haserror", true);
                    model.addAttribute("error", ex.getMessage());
                    return "login";
                }
            }
        }

        model.addAttribute("haserror", true);
        model.addAttribute("error", "Bad credentials!");
        return "login";
    }
}
