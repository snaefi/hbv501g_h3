package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Service
public class PatternServiceImplementation implements PatternService {
    private PatternRepository patternRepository;

    @Autowired //for our constructor
    public PatternServiceImplementation(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    @Override
    public List<Pattern> findByName(String name){
        return patternRepository.findByName(name);
    }

    @Override
    public List<Pattern> findAll(){
        return patternRepository.findAll();
    }

    @Override
    public Pattern findById(Long ID){
        Pattern pattern = patternRepository.findById(ID).orElse(null);
        return pattern;
    }

    @Override
    public Pattern save(Pattern pattern){
        return patternRepository.save(pattern);
    }

    @Override
    public void delete(Pattern pattern){
        patternRepository.delete(pattern);
    }

    @Override
    public boolean updatePattern(Long ID, Long ownerID,
                                 List<Integer> newPatternMatrix,
                                 List<String> newColorScheme,
                                 String newName,
                                 Integer stitches,
                                 Integer rows,
                                 Integer numberOfColors) {
        Optional<Pattern> optionalPattern = patternRepository.findByIDAndOwnerID(ID, ownerID);
        if (optionalPattern.isPresent()) {
            Pattern pattern = optionalPattern.get();

            if (newPatternMatrix != null) {
                pattern.setPatternMatrix(newPatternMatrix);
            }

            if (newColorScheme != null) {
                pattern.setColorScheme(newColorScheme);
            }

            if (newName != null) {
                pattern.setName(newName);
            }
            pattern.setModificationDate(new Date());
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }


    @Override
    public boolean changePatternName(Long ID, Long ownerID, String newName) {
        Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
        if(pattern != null){
            pattern.setName(newName);
            pattern.setModificationDate(new Date());
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }

    @Override
    public boolean makePublic(Long ID, Long ownerID){
        Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
        if(pattern != null){
            pattern.setPublic(true);
            //Viljum við updatea last modification hér?
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }

    @Override
    public boolean makePrivate(Long ID, Long ownerID) {
        Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
        if (pattern != null) {
            pattern.setPublic(false);
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }

}
