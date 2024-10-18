package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    public String searchById(@RequestParam("id") long ID, Model model) {
        model.addAttribute("patternResult", patternService.findById(ID));
        model.addAttribute("userResult", userService.findById(ID));
        return "home";
    }

    @RequestMapping(value = "/patterns", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object getPatterns(@RequestParam(value = "id", required = false) Long ID) {
        if (ID != null) {
            Pattern patternResult = patternService.findById(ID);
            if (patternResult != null) {
                return patternResult;
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "pattern not found");
                return errorResponse;
            }
        } else {
            List<Pattern> allPatterns = patternService.findAll();
            return allPatterns;
        }
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Object getUsers(@RequestParam(value = "id", required = false) Long ID) {
        if (ID != null) {
            User userResult = userService.findById(ID);
            if (userResult != null) {
                return userResult;
            } else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "user not found");
                return errorResponse;
            }
        } else {
            List<User> allUsers = userService.findAll();
            return allUsers;
        }
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
        public String deletePattern(@PathVariable("id") long ID, Model model){
            Pattern patternToDelete = patternService.findById(ID); //business logic
            patternService.delete(patternToDelete);
            return "redirect:/";
        }

}
