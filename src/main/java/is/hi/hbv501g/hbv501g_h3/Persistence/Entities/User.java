package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username cannot be blank")
    private String username;

	@NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;


	@NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @ElementCollection
    private List<Long> favoritePatternsIds;

    @ElementCollection
    private List<String> sharedWithQueue;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Pattern> ownedPatterns = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String email, List<Long> favoritePatternsIds, List<String> sharedWithQueue, List<Pattern> ownedPatterns) {
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

    public List<String> getSharedWithQueue() {
        return sharedWithQueue;
    }

    public void setSharedWithQueue(List<String> sharedWithQueue) {
        this.sharedWithQueue = sharedWithQueue;
    }

    public List<Pattern> getOwnedPatterns() {
        return ownedPatterns;
    }

    public void setOwnedPatterns(List<Pattern> ownedPatterns) {
        this.ownedPatterns = ownedPatterns;
    }
}
