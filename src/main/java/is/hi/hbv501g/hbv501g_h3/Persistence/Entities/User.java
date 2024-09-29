package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;



public class User {
    private Long ID;
    private String username;
    private String password;
    private String email;
    private List<Pattern> favoritePatterns;
    private Queue<String> sharedWithQueue;

    public User() {
    }

    public User(String username, String password, String email, List<Pattern> favoritePatterns, Queue<String> sharedWithQueue) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoritePatterns = favoritePatterns;
        this.sharedWithQueue = sharedWithQueue;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pattern> getFavoritePatterns() {
        return favoritePatterns;
    }

    public void setFavoritePatterns(List<Pattern> favoritePatterns) {
        this.favoritePatterns = favoritePatterns;
    }

    public Queue<String> getSharedWithQueue() {
        return sharedWithQueue;
    }

    public void setSharedWithQueue(Queue<String> sharedWithQueue) {
        this.sharedWithQueue = sharedWithQueue;
    }
}
