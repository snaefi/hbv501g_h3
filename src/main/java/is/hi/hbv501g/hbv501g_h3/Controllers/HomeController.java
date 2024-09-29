package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.ui.Model;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    private PatternService patternService;
    private UserService userService;

    @Autowired
    public HomeController(PatternService patternService, UserService userService) {
        this.patternService = patternService;
        this.userService = userService;
    }

    @RequestMapping("/")
    public String homePage(Model model) {
        //Call a method in a Service Class
        model.addAttribute("patterns",patternService.findAll());
        model.addAttribute("users",userService.findAll());
        //Add some data to the model
        return "home";
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchById(@RequestParam("id") long id, Model model) {
        model.addAttribute("patternResult", patternService.findByID(id));
        model.addAttribute("userResult", userService.findByID(id));
        return "home";
    }


    @RequestMapping(value="/createpattern", method = RequestMethod.GET)
    public String createPatternForm(Pattern pattern) {
        return "newPattern";
    }

    @RequestMapping(value="/createpattern", method=RequestMethod.POST)
    public String createPattern(Pattern pattern, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "newPattern";
        }
        patternService.save(pattern);
        return "redirect:/";
    }

    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
        public String deletePattern(@PathVariable("id") long id, Model model){
            Pattern patternToDelete = patternService.findByID(id); //business logic
            patternService.delete(patternToDelete);
            return "redirect:/";
        }

}
