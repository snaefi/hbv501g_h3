package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import is.hi.hbv501g.hbv501g_h3.util.ConsistentRowLength;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @ElementCollection
    @NotEmpty(message = "Pattern matrix is required")
    @ConsistentRowLength
    private List<String> patternMatrix;

    @ElementCollection
    @NotEmpty(message = "Color codes cant be empty")
    private List<String> colorCodes;

    private Date creationDate = new Date();
    private Date modificationDate = new Date();

    private int likeCount = 0;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    //@NotNull(message = "An owner (user) must be specified.")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "pattern_collaborators",
            joinColumns = @JoinColumn(name = "pattern_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnore
    private List<User> collaborators = new ArrayList<>(); // List of users collaborating on this pattern

    public KnittingPattern() {
    }

    public KnittingPattern(String title, Boolean isPublic, List<String> patternMatrix, List<String> colorCodes, User owner) {
        this.title = title;
        this.isPublic = isPublic;
        this.patternMatrix = patternMatrix;
        this.owner = owner;

        this.imageUrl = "https://i.ibb.co/ScdWZ38/5x5.png"; // 5x5 test pattern as default for now
        this.colorCodes = colorCodes;
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

    public int getLikeCount() {
        return likeCount;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    // Custom getter to return the owner's username
    @JsonProperty("ownerUsername")
    public String getOwnerUsername() {
        return owner != null ? owner.getUsername() : null;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public @NotEmpty(message = "Pattern matrix is required") List<String> getPatternMatrix() {
        return patternMatrix;
    }

    public void setPatternMatrix(@NotEmpty(message = "Pattern matrix is required") List<String> patternMatrix) {
        this.patternMatrix = patternMatrix;
    }

    public @NotEmpty(message = "Color codes cant be empty") List<String> getColorCodes() {
        return colorCodes;
    }

    public void setColorCodes(@NotEmpty(message = "Color codes cant be empty") List<String> colorCodes) {
        this.colorCodes = colorCodes;
    }

    public List<User> getCollaborators() {
        return collaborators;
    }

    public void addCollaborator(User user) {
        if (!collaborators.contains(user)) {
            collaborators.add(user);
        }
    }

    public void removeCollaborator(User user) {
        collaborators.remove(user);
    }

    @JsonProperty("collaboratorNames")
    public List<String> getCollaboratorUsernames() {
        return collaborators.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
