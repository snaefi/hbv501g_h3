package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PatternServiceImplementation implements PatternService{
    private List<Pattern> patternRepository = new ArrayList<>();
    private int id_counter = 0;

    @Autowired //for our constructor
    public PatternServiceImplementation(){
        // Create 3 random patterns for our dummy repo. To be removed when JPA added.
        patternRepository.add();

        //JPA gives each Pattern an ID, but here we add them manually
        for(Pattern p: patternRepository){
            p.setID(id_counter);
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
    public Pattern findByID(long ID){
        for(Pattern p: patternRepository){
            if(p.getID() == ID){
                return p;
            };
        }
        return null; //throw error instead of returning null
    }

    @Override
    public Pattern save(Pattern pattern){
        pattern.setID(id_counter);
        id_counter++;
        patternRepository.add(pattern);
        return pattern;
    }

    @Override
    public void delete(Pattern pattern){
        patternRepository.remove(pattern);
    }
}
