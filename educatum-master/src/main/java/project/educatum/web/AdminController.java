package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Teacher;
import project.educatum.model.Subject;
import project.educatum.model.Student;
import project.educatum.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class AdminController {

    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final TeacherSubjectService teacherSubjectService;
    private final StudentService studentService;
    private final PaymentService paymentService;
    private final TeacherStudentService teacherStudentService;
    private final EmailService emailService;

    public AdminController(TeacherService teacherService, SubjectService subjectService, TeacherSubjectService teacherSubjectService, StudentService studentService, PaymentService paymentService, TeacherStudentService teacherStudentService, EmailService emailService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.teacherSubjectService = teacherSubjectService;
        this.studentService = studentService;
        this.paymentService = paymentService;
        this.teacherStudentService = teacherStudentService;
        this.emailService = emailService;
    }

    @GetMapping("/allTeachers")
    public String getAllTeachers(@RequestParam(required = false) String ime, Model model) {
        List<Teacher> teachers = new ArrayList<>();
        if (ime == null) {
            teachers = teacherService.findAll();
        } else {
            teachers = teacherService.findAllByNameLike(ime);
        }

        model.addAttribute("teachers", teachers);
        return "teachersAdmin";
    }

    @GetMapping("/allSubjects")
    public String getAllSubjects(@RequestParam(required = false) String ime, Model model) {
        List<Subject> subjects;
        if (ime == null) {
            subjects = subjectService.findAll();
        } else {
            subjects = subjectService.findAllByNameLike(ime);
        }
        model.addAttribute("subjects", subjects);
        return "subjectsAdmin";
    }

    @GetMapping("/allStudents")
    public String getAllStudents(@RequestParam(required = false) String ime, Model model) {
        List<Student> students;
        if (ime == null) {
            students = studentService.findAll();
        } else {
            students = studentService.findAllByName(ime);
        }
        model.addAttribute("students", students);
        return "studentsAdmin";
    }

    @PostMapping("/activate/{id}")
    public String activateAccount(@PathVariable String id) {
        Teacher n = teacherService.findById(Integer.valueOf(id));
        if (n != null) {
            teacherService.updateEnabled(Integer.valueOf(id));
            emailService.sendMessage(n.getEmail(), "Активиран корисник - EDUCATUM", "Вашиот профил е активиран. Можете да се најавите со e-mail и лозинка. \n\n EDUCATUM");
        }
        return "redirect:/admin/allTeachers";
    }

    @GetMapping("/add")
    public String showAdd() {
        return "formAdd.html";
    }

    @PostMapping("/add/subject")
    public String create(@RequestParam String ime) {
        subjectService.create(ime);
        return "redirect:/admin/allSubjects";
    }

}
