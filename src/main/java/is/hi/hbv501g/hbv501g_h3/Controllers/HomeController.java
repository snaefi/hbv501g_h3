package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.ui.Model;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private PatternService                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             patternService;

    @Autowired
    public HomeController(PatternService patternService) {
        this.patternService = patternService;
    }

    @RequestMapping("/")
    public String homePage(Model model) {
        //Call a method in a Service Class
        model.addAttribute("patterns",patternService.findAll());
        //Add some data to the model
        return "home";
    }
}
