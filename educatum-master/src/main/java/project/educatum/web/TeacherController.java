package project.educatum.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Class;
import project.educatum.model.Student;
import project.educatum.model.Teacher;
import project.educatum.model.Subject;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/teachers", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class TeacherController {

    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final TeacherSubjectService teacherSubjectService;
    private final StudentService studentService;
    private final PaymentService paymentService;
    private final TeacherStudentService teacherStudentService;
    private final ClassService classService;

    public TeacherController(TeacherService teacherService, SubjectService subjectService, TeacherSubjectService teacherSubjectService, StudentService studentService, PaymentService paymentService, TeacherStudentService teacherStudentService, ClassService classService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.teacherSubjectService = teacherSubjectService;
        this.studentService = studentService;
        this.paymentService = paymentService;
        this.teacherStudentService = teacherStudentService;
        this.classService = classService;
    }

    @GetMapping
    public String getAllTeachers(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teachers";
    }

    @GetMapping("/allStudents")
    public String getAllStudentsByTeacher(@RequestParam(required = false) String ime, Model model, HttpServletRequest request) {
        List<Student> students = new ArrayList<>();
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        if (ime == null) {
            students = teacherService.getStudentsByTeacher(teacher.getId());
        } else {
            students = this.studentService.findAllByNameLike(ime, teacherService.getStudentsByTeacher(teacher.getId()));
        }
        model.addAttribute("teacher", teacherService.findById(teacher.getId()));
        model.addAttribute("students", students);
        return "evidencija";
    }

    @PostMapping("/delete/{id}")
    public String deleteTeacher(@PathVariable String id) {
        teacherService.delete(Integer.parseInt(id));
        return "redirect:/admin/allTeachers";
    }


    @PostMapping("/payments/{id}")
    public String paymentForStudent(@PathVariable String id, Model model, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        Integer owes = paymentService.studentTeacherLoan(Integer.valueOf(id), teacher.getId());
        model.addAttribute("owes", owes);
        Integer numScheduledClasses = teacherStudentService.find(teacher.getId(), Integer.valueOf(id)).getnumScheduledClasses();
        model.addAttribute("numScheduledClasses", numScheduledClasses);
        Integer priceByClass = teacherStudentService.find(teacher.getId(), Integer.valueOf(id)).getpriceByClass();
        model.addAttribute("priceByClass", priceByClass);
        Integer numListenedClasses = paymentService.numListenedClasses(Integer.valueOf(id), teacher.getId());
        model.addAttribute("numListenedClasses", numListenedClasses);
        model.addAttribute("student", studentService.findById(Integer.valueOf(id)));
        return "payment";
    }

    @PostMapping("/updatePayment")
    public String updatePaymentForStudent(@RequestParam String studentId, Model model, HttpServletRequest request,
                                          @RequestParam String price, @RequestParam String numPaidClasses) {

        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        teacherService.addPayment(teacher.getId(), Integer.valueOf(price));
        

        Integer owes = paymentService.studentTeacherLoan(Integer.valueOf(studentId), teacher.getId());
        model.addAttribute("owes", owes);
        Integer numScheduledClasses = teacherStudentService.find(teacher.getId(), Integer.valueOf(studentId)).getnumScheduledClasses();
        model.addAttribute("numScheduledClasses", numScheduledClasses);
        Integer priceByClass = teacherStudentService.find(teacher.getId(), Integer.valueOf(studentId)).getpriceByClass();
        model.addAttribute("priceByClass", priceByClass);
        Integer numListenedClasses = paymentService.numListenedClasses(Integer.valueOf(studentId), teacher.getId());
        model.addAttribute("numListenedClasses", numListenedClasses);
        model.addAttribute("student", studentService.findById(Integer.valueOf(studentId)));
        return "payment";
    }


    @GetMapping("/allClasses")
    public String timetable(Model model, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        List<Class> classes = teacherService.getClassesByTeacher(teacher.getId());
        model.addAttribute("teacher", teacherService.findById(teacher.getId()));
        model.addAttribute("classes", classes);
        return "raspored";
    }


    @GetMapping("/allSubjects")
    public String getAllSubjectsByTeacher(@RequestParam(required = false) String ime, Model model, HttpServletRequest request) {
        List<Subject> subjects = new ArrayList<>();
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        if (ime == null) {
            subjects = teacherService.getSubjectsByTeacher(teacher.getId());
        } else {
            subjects = this.subjectService.findAllByNameAndTeacherLike(ime, teacherService.getSubjectsByTeacher(teacher.getId()));
        }
        model.addAttribute("teacher", teacherService.findById(teacher.getId()));
        model.addAttribute("subjects", subjects);
        return "subjectsByTeacher";
    }


    @PostMapping("/addStudentForm")
    public String addStudentForm() {
        return "addNewStudent";
    }

    @PostMapping("/addSubjectForm")
    public String addSubjectForm(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "addNewSubject";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String price, @RequestParam String numClasses,
                             @RequestParam String email, HttpServletRequest request) {
        Student ucenik = studentService.findByEmail(email);
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());

        teacherService.addStudent(teacher.getId(), ucenik.getId(), Integer.valueOf(price), Integer.valueOf(numClasses));
        return "redirect:/teachers/allStudents";
    }


    @PostMapping("/addSubject")
    public String addSubject(Model model, HttpServletRequest request,
                             @RequestParam String subjectId,
                             @RequestParam String desc) {

        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        teacherService.addSubject(teacher.getId(), Integer.valueOf(subjectId), desc);

        return "redirect:/teachers/allSubjects";
    }

    @PostMapping("/profile")
    public String showProfile(Model model, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        model.addAttribute("ime", teacher.getName() + " " + teacher.getSurname());
        model.addAttribute("opis", teacher.getDescription());
        model.addAttribute("email", teacher.getEmail());
        model.addAttribute("tel", teacher.getMobileNumber());
        return "userInfo";
    }


    @PostMapping("/teachesSubject")
    public String teachesSubject(@RequestParam String tema, @RequestParam String subjectID, HttpServletRequest request) {
        Optional<Subject> p = subjectService.findById(Integer.valueOf(subjectID));
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        String username = user.getUsername();
        Teacher n = teacherService.findByEmail(username);
        teacherSubjectService.addSubject(n.getId(), Integer.valueOf(subjectID), tema);
        return "redirect:/home/document";
    }

//OVIE DVA METODI PODOLU DA SE DOVRSAT ZA DODAVANJE NA CHAS

    @PostMapping("/addClassForm")
    public String addClassForm(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "addNewClass";
    }

    @PostMapping("/addClass")
    public String addClass(
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam String desc,
            @RequestParam String ime,
            HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher t = teacherService.findByEmail(user.getUsername());
        Subject s = subjectService.findByName(ime);
        classService.addClass(date, desc, t.getId(), s.getId());
        return "redirect:/teachers/allClasses";
    }

    @PostMapping("/showProfileTeacher/{id}")
    public String showProfileTeacher(@PathVariable String id, Model model) {
        Teacher teacher = teacherService.findById(Integer.valueOf(id));
        model.addAttribute("teacher", teacher);
        return "showProfileTeacher.html";
    }


}
