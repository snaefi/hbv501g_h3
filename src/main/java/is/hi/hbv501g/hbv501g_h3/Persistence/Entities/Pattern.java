package is.hi.hbv501g.hbv501g_h3.Persistence.Entities;

import java.util.List;
import java.util.Date;

public class Pattern {
    private Long ID;
    private Long ownerID;
    private String name;
    private List<Integer> patternMatrix;
    private Integer rows;
    private Integer stitches;
    private Integer numberOfColors;
    private List<String> colorScheme;
    private Date creationDate;
    private Date modificationDate;
    private Boolean isPublic;

    public Pattern() {
    }

    public Pattern(Long ownerID, String name, List<Integer> patternMatrix, Integer rows, Integer stitches, Integer numberOfColors, List<String> colorScheme, Date creationDate, Date modificationDate, Boolean isPublic) {
        this.ownerID = ownerID;
        this.name = name;
        this.patternMatrix = patternMatrix;
        this.rows = rows;
        this.stitches = stitches;
        this.numberOfColors = numberOfColors;
        this.colorScheme = colorScheme;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.isPublic = isPublic;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Long ownerID) {
        this.ownerID = ownerID;
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
}
