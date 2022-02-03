package project.educatum.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.educatum.model.Casovi;
import project.educatum.model.Nastavnici;
import project.educatum.service.CasoviService;
import project.educatum.service.NastavniciService;
import project.educatum.service.PredmetiService;
import project.educatum.service.UceniciService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(path = "/raspored", method = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.GET})
public class CasoviController {
    private final NastavniciService nastavniciService;
    private final CasoviService casoviService;
    private final PredmetiService predmetiService;
    private final UceniciService uceniciService;

    public CasoviController(NastavniciService nastavniciService, CasoviService casoviService, PredmetiService predmetiService, UceniciService uceniciService) {
        this.nastavniciService = nastavniciService;
        this.casoviService = casoviService;
        this.predmetiService = predmetiService;
        this.uceniciService = uceniciService;
    }

}
