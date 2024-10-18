package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

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
}
