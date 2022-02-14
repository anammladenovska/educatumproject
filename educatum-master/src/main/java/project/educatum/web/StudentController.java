package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.educatum.model.Student;
import project.educatum.service.TeacherService;
import project.educatum.service.SubjectService;
import project.educatum.service.StudentService;
import project.educatum.service.InterestService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
@RequestMapping(path = "/students", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class StudentController {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final InterestService interestService;
    private final TeacherService teacherService;

    public StudentController(StudentService studentService, SubjectService subjectService, InterestService interestService, TeacherService teacherService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.interestService = interestService;
        this.teacherService = teacherService;
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


}
