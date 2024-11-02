package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;

public interface AuthenticationService {
    String login(String username, String password);
    User getProfile(String token);
}
