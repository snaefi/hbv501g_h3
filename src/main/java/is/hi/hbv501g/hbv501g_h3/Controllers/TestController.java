package is.hi.hbv501g.hbv501g_h3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
// import org.hibernate.mapping.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class TestController {
    private PatternService patternService;
    private UserService userService;

    @Autowired
    public TestController(PatternService patternService, UserService userService) {
        this.patternService = patternService;
        this.userService = userService;
    }


	    @GetMapping("/")
    public Object homePage(Model model) {
    //    Map<String, Object> response = new HashMap<>();

        // Return the map wrapped in a ResponseEntity, which Spring Boot will serialize to JSON
        return ResponseEntity.ok(Map.of("test",1,"message","hello"));
    }


	// @GetMapping(value = "/users", produces = "application/json")
    // @ResponseBody
    // public Page<User> getUsers(
	// 	@RequestParam(value = "id", required = false) Long ID,
	// 	@RequestParam(defaultValue = "0") int page,
	// 	@RequestParam(defaultValue = "10") int size
	// 	) {

    //         // Page<User> allUsers = userService.findAll(page, size);
    //         // return allUsers;
    //     }

}
