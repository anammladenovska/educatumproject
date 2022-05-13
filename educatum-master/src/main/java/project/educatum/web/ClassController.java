package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.educatum.repository.ClassRepository;
import project.educatum.service.ClassService;
import project.educatum.service.TeacherService;
import project.educatum.service.SubjectService;
import project.educatum.service.StudentService;

@Controller
@RequestMapping(path = "/raspored", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class ClassController {

    private final ClassRepository classRepository;

    public ClassController(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @PostMapping("/delete/{id}")
    public String deleteClass(@PathVariable String id) {
        classRepository.deleteById(Integer.valueOf(id));
        return "redirect:/teachers/allClasses";
    }

}
