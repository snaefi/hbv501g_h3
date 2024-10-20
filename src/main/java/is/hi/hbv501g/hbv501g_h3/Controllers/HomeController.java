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

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<RepresentationModel<?>> getRootLinks() {
        RepresentationModel<?> rootResource = new RepresentationModel<>();

        // Create default Pageable for users (e.g., page 0, size 5, sorting by ID in ascending order)
        Pageable defaultPageable = PageRequest.of(0, 5); // page = 0, size = 5

        // Create links for users with default pagination parameters
        Link usersLink = WebMvcLinkBuilder
            .linkTo(WebMvcLinkBuilder
                .methodOn(UserController.class)
                .getAllUsers(null, defaultPageable, null))
            .withRel("users");
        rootResource.add(usersLink);

        // Create links for patterns
        Link patternsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PatternController.class).getPatterns(null, null, true, 0, 10, "ID", "asc", null)).withRel("patterns");
        rootResource.add(patternsLink);

        return ResponseEntity.ok(rootResource);
    }
    // @RequestMapping("/")
    // public String homePage(Model model) {
    //     //Call a method in a Service Class
    //     model.addAttribute("patterns",patternService.findAllPatternsPaginated(0,5));
    //     // model.addAttribute("users",userService.findAll(0, 5));
    //     //Add some data to the model
    //     return "home";
    // }

    // @RequestMapping(value = "/search", method = RequestMethod.GET)
    // public String searchById(@RequestParam("id") long ID, Model model) {
    //     model.addAttribute("patternResult", patternService.findById(ID));
    //     model.addAttribute("userResult", userService.findById(ID));
    //     return "home";
    // }

    // @RequestMapping(value = "/patterns", method = RequestMethod.GET, produces = "application/json")
    // @ResponseBody
    // public Object getPatterns(
	// 	@RequestParam(value = "id", required = false) Long ID
	// 	) {
    //     if (ID != null) {
    //         Pattern patternResult = patternService.findById(ID);
    //         if (patternResult != null) {
    //             return patternResult;
    //         } else {
    //             Map<String, String> errorResponse = new HashMap<>();
    //             errorResponse.put("error", "pattern not found");
    //             return errorResponse;
    //         }
    //     } else {
    //         Page<Pattern> allPatternsPage = patternService.findAllPatternsPaginated(0,5);
    //         return allPatternsPage;
    //     }
    // }

    // @RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/json")
    // @ResponseBody
    // public Object getUsers(
	// 	@RequestParam(value = "id", required = false) Long ID,
	// 	@RequestParam(defaultValue = "0") int page,
	// 	@RequestParam(defaultValue = "10") int size
		
	// 	) {
    //     if (ID != null) {
    //         User userResult = userService.findById(ID);
    //         if (userResult != null) {
    //             return userResult;
    //         } else {
    //             Map<String, String> errorResponse = new HashMap<>();
    //             errorResponse.put("error", "user not found");
    //             return errorResponse;
    //         }
    //     } else {
    //         Page<User> allUsers = userService.findAll(page, size);
    //         return allUsers;
    //     }
    // }

    // @RequestMapping(value="/createpattern", method = RequestMethod.GET)
    // public String createPatternForm(Pattern pattern) {
    //     return "newPattern";
    // }

    // @RequestMapping(value="/createpattern", method=RequestMethod.POST)
    // public String createPattern(Pattern pattern, BindingResult result, Model model){
    //     if(result.hasErrors()) {
    //         return "newPattern";
    //     }
    //     patternService.save(pattern);
    //     return "redirect:/";
    // }

    // @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    //     public String deletePattern(@PathVariable("id") long ID, Model model){
    //         Pattern patternToDelete = patternService.findById(ID); //business logic
    //         patternService.delete(patternToDelete);
    //         return "redirect:/";
    //     }

}
