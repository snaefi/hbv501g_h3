package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to User
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "pattern_id", nullable = false) // Foreign key to Pattern
    @JsonIgnore
    private KnittingPattern pattern;

    private Date timestamp = new Date();

    public enum NotificationType {
        MESSAGE, ACCEPT_DECLINE
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public KnittingPattern getPattern() {
        return pattern;
    }

    public void setPattern(KnittingPattern pattern) {
        this.pattern = pattern;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("patternOwner")
    public String getPatternOwner() {
        return pattern != null && pattern.getOwner() != null ? pattern.getOwner().getUsername() : null;
    }
}
