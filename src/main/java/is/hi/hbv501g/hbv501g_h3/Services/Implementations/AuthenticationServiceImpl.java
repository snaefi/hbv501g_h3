package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;  // JWT utility to generate tokens

    @Autowired
    private PasswordEncoder passwordEncoder;  // To compare the passwords

    @Override
    public String login(String username, String password) {
        // Step 1: Find the user by username
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new ApiExceptions.UsernameNotFoundException(username));

        // Step 2: Compare the raw password with the hashed password stored in the database
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ApiExceptions.InvalidCredentialsException();
        }

        // Step 3: Generate a JWT token for the user
        return jwtUtil.generateToken(user);
    }

    public User getProfile(String token) {
        return jwtUtil.getUserFromToken(token)
                .orElseThrow(ApiExceptions.InvalidTokenException::new);
    }
}
