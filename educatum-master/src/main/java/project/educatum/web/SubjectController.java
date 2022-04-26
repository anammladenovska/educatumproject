package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.educatum.service.SubjectService;
import project.educatum.service.StudentService;
import project.educatum.service.InterestService;

@Controller
@RequestMapping(path = "/subjects", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})

public class SubjectController {

    private final StudentService studentService;
    private final SubjectService subjectService;
    private final InterestService interestService;

    public SubjectController(StudentService studentService, SubjectService subjectService, InterestService interestService) {
        this.studentService = studentService;
        this.subjectService = subjectService;
        this.interestService = interestService;
    }


    @PostMapping("/delete/{id}")
    public String deleteSubject(@PathVariable String id) {
        subjectService.delete(Integer.parseInt(id));
        return "redirect:/admin/allSubjects";
    }

}
