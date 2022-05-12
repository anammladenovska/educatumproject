package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Class;
import project.educatum.model.Homework;
import project.educatum.model.Student;
import project.educatum.model.Teacher;
import project.educatum.model.relations.StudentHomeworkRelation;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "/students", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class StudentController {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final InterestService interestService;
    private final TeacherService teacherService;
    private final PaymentService paymentService;
    private final TeacherStudentService teacherStudentService;
    private final HomeworkService homeworkService;
    private final StudentHomeworkService studentHomeworkService;
    private final ClassService classService;

    public StudentController(StudentService studentService, SubjectService subjectService, InterestService interestService, TeacherService teacherService, PaymentService paymentService, TeacherStudentService teacherStudentService, HomeworkService homeworkService, StudentHomeworkService studentHomeworkService, ClassService classService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.interestService = interestService;
        this.teacherService = teacherService;
        this.paymentService = paymentService;
        this.teacherStudentService = teacherStudentService;
        this.homeworkService = homeworkService;
        this.studentHomeworkService = studentHomeworkService;
        this.classService = classService;
    }

    @GetMapping("/listSubjectsTeachers")
    public String listSubjectsTeachers(Model model, @RequestParam(required = false) String subjectID) {
        model.addAttribute("subject", subjectService.findById(Integer.valueOf(subjectID)));
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.getAllTeachersBySubject(Integer.valueOf(subjectID)));
        return "listSubTeachStudents";
    }

    @PostMapping("/teachers")
    public String getAllTeachersBySubject(Model model, @RequestParam String subjectID) {
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.getAllTeachersBySubject(Integer.valueOf(subjectID)));
        return "listSubTeachStudents";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.delete(Integer.parseInt(id));
        return "redirect:/admin/allStudents";
    }

    @PostMapping("/slusanje")
    public String slusanje(Model model, HttpServletRequest request) {
        return "";
    }

    @PostMapping("/zaintesesiranZaPredmet")
    public String zaintesesiranZaPredmet(@RequestParam String subjectID,
                                         HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        LocalDate localDate = LocalDate.now();
        String username = user.getUsername();
        Student u = studentService.findByEmail(username);
        interestService.addSubjectStudent(Integer.valueOf(subjectID), u.getId(), localDate);
        return "redirect:/zaintesesiranZaPredmet";
    }

    @PostMapping("/listenedClass")
    public String listenedClass(Model model, HttpServletRequest request,
                                @RequestParam String studentID) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher t = teacherService.findByEmail(user.getUsername());
        model.addAttribute("classes", teacherService.getClassesByTeacher(t.getId()));
        model.addAttribute("student", studentService.findById(Integer.valueOf(studentID)));
        return "listeningForm";
    }

    @PostMapping("/addListening")
    public String addListening(Model model, @RequestParam String studentID,
                               @RequestParam(required = false) String price,
                               @RequestParam String classID, HttpServletRequest request) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.findByEmail(user.getUsername());
        studentService.addListening(Integer.valueOf(studentID), Integer.valueOf(classID), teacher.getId());

        if (price != null)
            paymentService.addPayment(teacher.getId(), Integer.valueOf(price), Integer.valueOf(classID), Integer.valueOf(studentID));


        Integer owes = paymentService.studentTeacherLoan(Integer.valueOf(studentID), teacher.getId());
        model.addAttribute("owes", owes);
        Integer numScheduledClasses = teacherStudentService.find(teacher.getId(), Integer.valueOf(studentID)).getNumScheduledClasses();
        model.addAttribute("numScheduledClasses", numScheduledClasses);
        Integer priceByClass = teacherStudentService.find(teacher.getId(), Integer.valueOf(studentID)).getPriceByClass();
        model.addAttribute("priceByClass", priceByClass);
        Integer numListenedClasses = paymentService.numListenedClasses(Integer.valueOf(studentID), teacher.getId());
        model.addAttribute("numListenedClasses", numListenedClasses);
        model.addAttribute("classes", teacherService.getClassesByTeacher(teacher.getId()));
        model.addAttribute("student", studentService.findById(Integer.valueOf(studentID)));
        return "payment";
    }


    @PostMapping("/showProfileStudent")
    public String showProfileStudent(HttpServletRequest request, Model model) {
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Student student = studentService.findByEmail(user.getUsername());
        model.addAttribute("student", student);
        return "showProfileStudent.html";
    }

    @PostMapping("/homeWork")
    public String getAllHomeworks(@RequestParam(required = false) String opis, Model model){
        List<Homework> homeworks;
        if(opis == null){
            homeworks = homeworkService.findAll();
        } else {
            homeworks = homeworkService.findAllByDescriptionLike(opis);
        }
        model.addAttribute("homeworks", homeworks);
        return "homeworksList";
    }

    @PostMapping("/add/homework")
    public String create(@RequestParam String opis, @RequestParam String email, @RequestParam String tema) {
        Teacher teacher = teacherService.findByEmail(email);
        Class aClass = classService.findByTopic(tema);
        homeworkService.create(opis, teacher.getId(), aClass.getId());
        return "redirect:/students/homeWork";
    }

    @PostMapping("/done/{id}")
    public String doneHomework(@PathVariable String id) {
        StudentHomeworkRelation h = studentHomeworkService.findById(Integer.valueOf(id));
        if(h != null){
            studentHomeworkService.updateDone(Integer.valueOf(id));
        }
        return "redirect:/students/homeWork";
    }

}
