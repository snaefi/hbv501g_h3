package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;


    @GetMapping
    public User getIdentity(@RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        return authenticationService.getProfile(token);
    }

    @PostMapping("/uploadPicture")
    @ResponseStatus(HttpStatus.OK)
    public void uploadProfilePicture(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                       @RequestParam("file") MultipartFile file) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        // Handle the uploaded file
        try {
            byte[] pictureBytes = file.getBytes();
            userService.uploadProfileImage(user, pictureBytes);
        } catch (IOException e) {
            throw new ApiExceptions.ProfilePictureUploadException();
        }
    }

}