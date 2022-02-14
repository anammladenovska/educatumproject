package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.educatum.model.Admin;
import project.educatum.model.Student;
import project.educatum.model.Teacher;
import project.educatum.model.Subject;
import project.educatum.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final SubjectService subjectService;
    private final AdminService adminService;
    private final TeacherService teacherService;
    private final QualificationService qualificationService;
    private final StudentService studentService;
    //    private static String UPLOADED_FOLDER = "C://Users//Acer//Desktop//dok//";
    private static String UPLOADED_FOLDER = "C://Users//User//OneDrive//Desktop//kvalifikacii//";

    public HomeController(SubjectService subjectService, AdminService adminService, TeacherService teacherService, QualificationService qualificationService, StudentService studentService) {
        this.subjectService = subjectService;
        this.adminService = adminService;
        this.teacherService = teacherService;
        this.qualificationService = qualificationService;
        this.studentService = studentService;
    }

    @GetMapping
    public String getHomePage() {
        return "home";
    }

    @GetMapping("/chooseSubject")
    public String choose(@RequestParam(required = false) String ime, Model model) {
        List<Subject> subjects;
        if (ime == null) {
            subjects = this.subjectService.findAll();
        } else {
            subjects = this.subjectService.findAllByNameLike(ime);
        }
        model.addAttribute("subjects", subjects);
        return "chooseSubjects.html";
    }

    @GetMapping("/document")
    public String document() {
        return "prikaziDokument.html";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, HttpServletRequest request) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Внесете задолжително документ");
            return "redirect:/home/potvrda";
        }

        try {

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            UserDetails userDetails = (UserDetails) request.getSession().getAttribute("user");
            Teacher n = teacherService.findByEmail(userDetails.getUsername());
            qualificationService.insert(String.valueOf(path), n.getId());
            redirectAttributes.addFlashAttribute("message",
                    "Ви благодариме за регистрацијата!\n Ќе добиете известување на e-mail кога Вашиот профил ќе биде активиран.");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/home/potvrda";
    }

    @GetMapping("/potvrda")
    public String potvrdaZaNastavnik() {
        return "potvrdaNastavnik.html";
    }

    @GetMapping("/add")
    public String addSubject(Model model) {
        List<Admin> adminList = this.adminService.listAll();
        model.addAttribute("adminList", adminList);
        return "dodadiForma.html";
    }


    @PostMapping("/chooseSubject")
    public String createSubject(@RequestParam String ime) {
        this.subjectService.create(ime);
        return "redirect:/home/chooseSubject";
    }

    @GetMapping("/listenSubject")
    public String listenSubject(Model model, @RequestParam(required = false) String subject,
                                HttpServletRequest request) {
        if(subject==null){
            return "redirect:/zainteresiran?error=Ве%20молиме%20изберете%20предмет";
        }
        UserDetails user = (UserDetails) request.getSession().getAttribute("user");
        Student ucenik = studentService.findByEmail(user.getUsername());
        studentService.interestedIn(Integer.valueOf(subject), ucenik.getId());
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.getAllTeachersBySubject(Integer.valueOf(subject)));

        return "listenSubject";
    }

    @GetMapping("/zainteresiran")
    public String interested(Model model,@RequestParam(required = false) String search) {
        List<Subject> subjects;
        if (search == null) {
            subjects = this.subjectService.findAll();
        } else {
            subjects = this.subjectService.findAllByNameLike(search);
        }
        model.addAttribute("subjects", subjects);
        return "interested";
    }

    @PostMapping("/showSubjects")
    public String getAllTeachersBySubject(Model model, @RequestParam(required = false) String subjectID) {
        model.addAttribute("subject", subjectService.findById(Integer.valueOf(subjectID)));
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("teachers", teacherService.getAllTeachersBySubject(Integer.valueOf(subjectID)));
        return "listenSubject";
    }

}
