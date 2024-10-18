package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Tell JPA to map this field to the column 'id'
    private Long ID;
    private String username;
    private String password;
    private String email;
    @ElementCollection
    private List<Long> favoritePatternsIds;
    @ElementCollection
    private Queue<String> sharedWithQueue;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pattern> ownedPatterns = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String email, List<Long> favoritePatternsIds, Queue<String> sharedWithQueue, List<Pattern> ownedPatterns) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.favoritePatternsIds = favoritePatternsIds;
        this.sharedWithQueue = sharedWithQueue;
        this.ownedPatterns = ownedPatterns;
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

    public List<Long> getFavoritePatternsIds() {
        return favoritePatternsIds;
    }

    public void setFavoritePatternsIds(List<Long> favoritePatternsIds) {
        this.favoritePatternsIds = favoritePatternsIds;
    }

    public Queue<String> getSharedWithQueue() {
        return sharedWithQueue;
    }

    public void setSharedWithQueue(Queue<String> sharedWithQueue) {
        this.sharedWithQueue = sharedWithQueue;
    }

    public List<Pattern> getOwnedPatterns() {
        return ownedPatterns;
    }

    public void setOwnedPatterns(List<Pattern> ownedPatterns) {
        this.ownedPatterns = ownedPatterns;
    }
}
