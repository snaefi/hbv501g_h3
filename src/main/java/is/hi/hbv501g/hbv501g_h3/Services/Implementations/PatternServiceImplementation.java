package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Service
public class PatternServiceImplementation implements PatternService {
    private List<Pattern> patternRepository = new ArrayList<>();
    private Integer id_counter = 0;

    //@Autowired //for our constructor
    public PatternServiceImplementation(){
        // Let chatgpt create 3 random patterns for our dummy repo. To be removed when JPA added.
        Long ownerID1 = 1L;
        Long ownerID2 = 2L;
        Long ownerID3 = 3L;

        Pattern pattern1 = new Pattern(
                ownerID1,
                "Sunset Pattern",
                Arrays.asList(1, 0, 0, 1, 1, 0, 1, 0, 0), // Random pattern matrix
                3, // Rows
                3, // Stitches
                3, // Number of colors
                Arrays.asList("Red", "Orange", "Yellow"), // Color scheme
                new Date(), // Creation date
                new Date(), // Modification date
                true // Is public
        );

        Pattern pattern2 = new Pattern(
                ownerID2,
                "Ocean Waves",
                Arrays.asList(1, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0), // Random pattern matrix
                4, // Rows
                3, // Stitches
                2, // Number of colors
                Arrays.asList("Blue", "White"), // Color scheme
                new Date(), // Creation date
                new Date(), // Modification date
                false // Is public
        );

        Pattern pattern3 = new Pattern(
                ownerID3,
                "Forest Path",
                Arrays.asList(0, 1, 0, 1, 0, 1, 0, 1, 0), // Random pattern matrix
                3, // Rows
                3, // Stitches
                2, // Number of colors
                Arrays.asList("Green", "Brown"), // Color scheme
                new Date(), // Creation date
                new Date(), // Modification date
                true // Is public
        );

        patternRepository.add(pattern1);
        patternRepository.add(pattern2);
        patternRepository.add(pattern3);

        //JPA gives each Pattern an ID, but here we add them manually
        for(Pattern p: patternRepository){
            p.setID((long) id_counter);
            id_counter++;
        }
    }

    @Override
    public Pattern findByName(String name){
        for(Pattern p: patternRepository){
            if(p.getName().equals(name)){
                return p;
            }
        }
        return null; //throw error instead of returning null
    }

    @Override
    public List<Pattern> findAll(){
        return patternRepository;
    }

    @Override
    public Pattern findByID(Long ID){
        for(Pattern p: patternRepository){
            if(p.getID().equals(ID)){
                return p;
            }
        }
        return null; //throw error instead of returning null
    }

    @Override
    public Pattern save(Pattern pattern){
        pattern.setID((long) id_counter);
        id_counter++;
        patternRepository.add(pattern);
        return pattern;
    }

    @Override
    public void delete(Pattern pattern){
        patternRepository.remove(pattern);
    }
}
