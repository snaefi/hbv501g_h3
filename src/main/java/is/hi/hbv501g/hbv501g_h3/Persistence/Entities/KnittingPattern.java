package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "patterns")
@JsonIgnoreProperties(value = {"owner"}, allowSetters = true)
public class KnittingPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters.")
    private String title;

    @NotNull(message = "The public status must be specified")
    private Boolean isPublic;

    @NotEmpty(message = "Pattern matrix is required")
    private String patternMatrix;

    private Date creationDate = new Date();
    private Date modificationDate = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "An owner (user) must be specified.")
    private User owner;

    public KnittingPattern() {
    }

    public KnittingPattern(String title, Boolean isPublic, String patternMatrix, User owner) {
        this.title = title;
        this.isPublic = isPublic;
        this.patternMatrix = patternMatrix;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public @NotEmpty(message = "Pattern matrix is required") String getPatternMatrix() {
        return patternMatrix;
    }

    public void setPatternMatrix(@NotEmpty(message = "Pattern matrix is required") String patternMatrix) {
        this.patternMatrix = patternMatrix;
    }

    // Custom getter to return the owner's username
    @JsonProperty("ownerUsername")
    public String getOwnerUsername() {
        return owner != null ? owner.getUsername() : null;
    }
}
