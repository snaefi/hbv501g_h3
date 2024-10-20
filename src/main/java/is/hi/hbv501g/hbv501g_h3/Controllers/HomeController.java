package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {
    private PatternService patternService;
    private UserService userService;

    @Autowired
    public HomeController(PatternService patternService, UserService userService) {
        this.patternService = patternService;
        this.userService = userService;
    }
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getRootLinks() {
        Map<String, Object> rootResource = new HashMap<>();
        rootResource.put("users", Map.of("href", "/users", "method", List.of("GET", "POST"), "queries", List.of("page", "size", "search","sort")));
        rootResource.put("patterns", Map.of("href", "/patterns", "method", List.of("GET", "POST"), "queries", List.of("page", "size", "search","sort")));

        return ResponseEntity.ok(rootResource);
    }
		

    // @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    //     public String deletePattern(@PathVariable("id") long ID, Model model){
    //         Pattern patternToDelete = patternService.findById(ID); //business logic
    //         patternService.delete(patternToDelete);
    //         return "redirect:/";
    //     }

}
