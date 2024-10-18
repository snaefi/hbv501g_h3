package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatternController {
    private PatternService patternService;

    @Autowired
    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }

    @PutMapping("/patterns")
    public String changePatternName(
            @RequestParam("id") Long ID,
            @RequestParam("ownerID") Long ownerID,
            @RequestParam("newName") String newName) {
        boolean isUpdated = patternService.changePatternName(ID, ownerID, newName);

        if (isUpdated) {
            return "redirect:/patterns";
        }
        else {
            return "redirect:/error";
        }
    }
}


