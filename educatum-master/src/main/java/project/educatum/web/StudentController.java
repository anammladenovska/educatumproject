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
import project.educatum.model.relations.TeacherStudentRelation;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public String getAllHomeworks(@RequestParam(required = false) String desc, Model model, HttpServletRequest request) {
        List<Homework> homeworks;
        if (desc == null) {
            homeworks = homeworkService.findAll();
        } else {
            homeworks = homeworkService.findAllByDescriptionLike(desc);
        }
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Student student = studentService.findByEmail(user.getUsername());
        model.addAttribute("homeworks", homeworks);
        List<TeacherStudentRelation> teacherStudentRelationList = teacherStudentService.findAll();
        List<Teacher> teachers = new ArrayList<>();
        for (TeacherStudentRelation t : teacherStudentRelationList) {
            if (t.getId().getStudentID().equals(student.getId())) {
                teachers.add(teacherService.findById(t.getId().getTeacherID()));
            }
        }
        model.addAttribute("teachersByStudent", teachers);
        return "homeworksList";
    }

    @PostMapping("/add/homework")
    public String create(@RequestParam String desc, @RequestParam String topic, @RequestParam String teacher) {
        Class aClass = classService.findByTopic(topic);
        homeworkService.create(desc, teacherService.findById(Integer.valueOf(teacher)).getId(), aClass.getId());
        return "redirect:/students/homeWork";
    }

    @PostMapping("/done/{id}")
    public String doneHomework(@PathVariable String id) {
        StudentHomeworkRelation h = studentHomeworkService.findById(Integer.valueOf(id));
        if (h != null) {
            studentHomeworkService.updateDone(Integer.valueOf(id));
        }

        return "redirect:/students/homeWork";
    }

    @PostMapping("/rateTeacher/{id}")
    public String rateTeacher(@PathVariable String id, Model model, @RequestParam String rating, HttpServletRequest request) {
        Float r = Float.valueOf(rating);
        Teacher t = teacherService.findById(Integer.valueOf(id));
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Student s = studentService.findByEmail(user.getUsername());
        if (studentService.rateTeacher(t, s, r)) {
            model.addAttribute("hasError", false);
            model.addAttribute("message", "Thank you for the rating!");
            model.addAttribute("teacher", t);
            model.addAttribute("teacherRating", teacherService.getRatingForTeacher(Long.valueOf(t.getId())));

            return "showProfileTeacher2.html";
        } else {
            model.addAttribute("hasError", true);
            model.addAttribute("message", "You don't have a permission to do this action!");
            model.addAttribute("teacher", t);
            model.addAttribute("teacherRating", teacherService.getRatingForTeacher(Long.valueOf(t.getId())));

            return "showProfileTeacher2.html";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Integer id, Model model) {
        Student student = this.studentService.findById(id);
        model.addAttribute("student", student);
        return "showProfileStudent";
    }

    @PostMapping("/{id}")
    public String editStudent(@PathVariable Integer id,
                              @RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String description,
                              @RequestParam String email,
                              @RequestParam String telephoneNumber,
                              HttpServletRequest request) {
        this.studentService.edit(id, name, surname,description,email,telephoneNumber);
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        if(!Objects.equals(email, user.getUsername())){
            return "redirect:/login";
        }
        return "redirect:/students/showProfileStudent";
    }

}
