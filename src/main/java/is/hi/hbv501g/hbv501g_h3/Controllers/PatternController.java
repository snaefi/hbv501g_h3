package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/patterns")
public class PatternController {
    private PatternService patternService;

    @Autowired
    public PatternController(PatternService patternService) {
        this.patternService = patternService;
    }
	
@GetMapping("")
public ResponseEntity<?> getPatterns(
    @RequestParam(value = "ownerId", required = false) Long ownerId,
    @RequestParam(value = "search", required = false) String searchTerm,
    @RequestParam(value = "isPublic", required = false) Boolean isPublic,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "sort", defaultValue = "ID") String sortBy,
    @RequestParam(value = "direction", defaultValue = "asc") String direction,
    PagedResourcesAssembler<Pattern> assembler
) {
    Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page, size, sort);
    
    // Default to public patterns if isPublic is not specified
    if (isPublic == null) {
        isPublic = true;
    }
    
    Page<Pattern> patternPage = patternService.findPatterns(ownerId, searchTerm, isPublic, pageable);
    
    // Convert Page to PagedModel for HATEOAS compliance
    PagedModel<EntityModel<Pattern>> pagedModel = assembler.toModel(patternPage);
    
    return ResponseEntity.ok(pagedModel);
}

	@PostMapping
    public ResponseEntity<Object> createPattern(@Valid @RequestBody Pattern pattern, BindingResult bindingResult) {
        // Handle validation errors
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        // Save the pattern if validation passes
        Pattern savedPattern = patternService.save(pattern);
        return new ResponseEntity<>(savedPattern, HttpStatus.CREATED);
    }
	@GetMapping("/{id}")
	public Pattern getPatternById(@PathVariable long id) {
		return patternService.findById(id);
	}

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updatePattern(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            Pattern updatedPattern = patternService.patchPattern(id, updates);
            return new ResponseEntity<>(updatedPattern, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(Map.of("NoSuchElementException",e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(Map.of("IllegalArgumentException",e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePattern(@PathVariable Long id) {
        patternService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content if deletion is successful
    }
    
	// @PutMapping("/patterns")
    // public String changePatternName(
    //         @RequestParam("id") Long ID,
    //         @RequestParam("ownerID") Long ownerID,
    //         @RequestParam("newName") String newName) {
    //     boolean isUpdated = patternService.changePatternName(ID, ownerID, newName);

    //     if (isUpdated) {
    //         return "redirect:/patterns";
    //     }
    //     else {
    //         return "redirect:/error";
    //     }
    // }
}


