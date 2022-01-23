package project.educatum.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.educatum.service.NastavniciService;

@Controller
@RequestMapping("/nastavnici")
public class NastavniciController {

    private final NastavniciService nastavniciService;

    public NastavniciController(NastavniciService nastavniciService) {
        this.nastavniciService = nastavniciService;
    }

    @GetMapping
    public String getAllTeachers(Model model){
        model.addAttribute("teachers",nastavniciService.findAll());
        return "nastavnici";
    }
}
