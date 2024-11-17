package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PatternService patternService;

    @Autowired
    AuthenticationService authenticationService;


    // Endpoint to get a User by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(id));
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(username));
    }

    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(value = "username", required = false) String username,
            @PageableDefault(size = 8) Pageable pageable
    ) {
        return userService.getAllUsers(username, pageable);
    }

    @GetMapping("/likedPatterns")
    public Page<KnittingPattern> getLikedPatterns(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        // Apply sorting based on parameters
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return patternService.getLikedPatternsByUser(user, title, username, sortedPageable);
    }

    // Create a new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.getUserById(id)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(id));

        userService.deleteUser(id);
    }

    // Patch user
    @PatchMapping("/{id}")
    public User patchUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User existingUser = userService.getUserById(id)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(id));

        updates.forEach((field, value) -> {
            switch (field) {
                case "username":
                    existingUser.setUsername((String) value);
                    break;
                case "password":
                    existingUser.setPassword((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        });

        return userService.updateUser(existingUser);
    }
}
