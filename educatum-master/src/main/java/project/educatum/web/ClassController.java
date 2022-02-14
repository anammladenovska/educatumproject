package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.educatum.service.ClassService;
import project.educatum.service.TeacherService;
import project.educatum.service.SubjectService;
import project.educatum.service.StudentService;

@Controller
@RequestMapping(path = "/raspored", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class ClassController {
    private final TeacherService teacherService;
    private final ClassService classService;
    private final SubjectService subjectService;
    private final StudentService studentService;

    public ClassController(TeacherService teacherService, ClassService classService, SubjectService subjectService, StudentService studentService) {
        this.teacherService = teacherService;
        this.classService = classService;
        this.subjectService = subjectService;
        this.studentService = studentService;
    }

}
