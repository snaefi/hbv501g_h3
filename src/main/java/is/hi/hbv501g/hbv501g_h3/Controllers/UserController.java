package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import jakarta.servlet.http.HttpSession;

import com.smattme.requestvalidator.RequestValidator;

// import org.hibernate.mapping.Set;
// import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;


import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
    private PatternService patternService;
    private UserService userService;

    @Autowired
    public UserController(PatternService patternService, UserService userService) {
        this.patternService = patternService;
        this.userService = userService;
    }

	@CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        EntityModel<User> userModel = EntityModel.of(user);

        // Add self link
        userModel.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserById(id)).withSelfRel());
        
        return userModel;
    }

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("")
	public PagedModel<EntityModel<User>> getAllUsers(
			@RequestParam(value = "search", required = false) String searchTerm,
			@PageableDefault(size = 5) Pageable pageable,
			PagedResourcesAssembler<User> assembler) {
	
		Page<User> userPage;
		if (searchTerm != null && !searchTerm.isEmpty()) {
			userPage = userService.searchUsers(searchTerm, pageable);
		} else {
			userPage = userService.findAll(pageable);
		}
	
		// Convert each User to EntityModel and add the self-link to each
		return assembler.toModel(userPage, user -> {
			EntityModel<User> userModel = EntityModel.of(user);
			userModel.add(WebMvcLinkBuilder.linkTo(
					WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getID()))
					.withSelfRel());
			return userModel;
		});
	}
	

	@CrossOrigin(origins = "http://localhost:3000")
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // If no validation errors, proceed with user creation
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
	@CrossOrigin(origins = "http://localhost:3000")
	    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return userService.patchUser(id, updates);
    }
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content if deletion is successful
    }
	
	}
	

    //End points to add
    // signup (GET, POST)
    // login (GET, POST)
    // loggedin (GET)

    // @RequestMapping(value = "/signup", method = RequestMethod.GET)
    // public String signupGET(User user){
    //     return "signup";
    // }

    // @RequestMapping(value = "/signup", method = RequestMethod.POST)
    // public String signupPOST(User user, BindingResult result, Model model){
    //     if(result.hasErrors()){
    //         return "redirect:/signup";
    //     }
    //     User exists = userService.findByUsername(user.getUsername());
    //     if(exists == null){
    //         userService.save(user);
    //     }
    //     return "redirect:/";
    // }

    // @RequestMapping(value = "/login", method = RequestMethod.GET)
    // public String loginGET(User user){
    //     return "login";
    // }

    // @RequestMapping(value = "/login", method = RequestMethod.POST)
    // public String loginPOST(User user, BindingResult result, Model model, HttpSession session){
    //     if(result.hasErrors()){
    //         return "login";
    //     }
    //     User exists = userService.login(user);
    //     if(exists != null){
    //         session.setAttribute("LoggedInUser", exists);
    //         model.addAttribute("LoggedInUser", exists);
    //         return "LoggedInUser";
    //     }
    //     return "redirect:/";
    // }

    // @RequestMapping(value = "/loggedin", method = RequestMethod.GET)
    // public String loggedinGET(HttpSession session, Model model){
    //     User sessionUser = (User) session.getAttribute("LoggedInUser");
    //     if(sessionUser  != null){
    //         model.addAttribute("LoggedInUser", sessionUser);
    //         return "loggedInUser";
    //     }
    //     return "redirect:/";
    // }