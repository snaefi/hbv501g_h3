package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

@Entity
@Table(name="patterns")
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ID;

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @ElementCollection
    @NotNull(message = "Pattern matrix is required")
    private List<Integer> patternMatrix;

    @Min(value = 1, message = "Rows must be at least 1")
    private Integer rows;

    @Min(value = 1, message = "Stitches must be at least 1")
    private Integer stitches;

    @Min(value = 1, message = "There must be at least 1 color")
    private Integer numberOfColors;

    @ElementCollection
    @NotNull(message = "Color scheme is required")
    private List<String> colorScheme;

    private Date creationDate = new Date();
    private Date modificationDate;

    private Boolean isPublic = true;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference // Prevents serialization of the owner reference
    private User owner;

    public Pattern() {
    }

    public Pattern(String name, List<Integer> patternMatrix, Integer rows, Integer stitches, Integer numberOfColors, List<String> colorScheme, Date creationDate, Date modificationDate, Boolean isPublic, User owner) {
        this.name = name;
        this.patternMatrix = patternMatrix;
        this.rows = rows;
        this.stitches = stitches;
        this.numberOfColors = numberOfColors;
        this.colorScheme = colorScheme;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.isPublic = isPublic;
        this.owner = owner;
    }
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPatternMatrix() {
        return patternMatrix;
    }

    public void setPatternMatrix(List<Integer> patternMatrix) {
        this.patternMatrix = patternMatrix;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getStitches() {
        return stitches;
    }

    public void setStitches(Integer stitches) {
        this.stitches = stitches;
    }

    public Integer getNumberOfColors() {
        return numberOfColors;
    }

    public void setNumberOfColors(Integer numberOfColors) {
        this.numberOfColors = numberOfColors;
    }

    public List<String> getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(List<String> colorScheme) {
        this.colorScheme = colorScheme;
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

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
