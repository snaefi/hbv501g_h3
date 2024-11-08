package is.hi.hbv501g.hbv501g_h3.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class ConsistentRowLengthValidator implements ConstraintValidator<ConsistentRowLength, List<String>> {

    @Override
    public void initialize(ConsistentRowLength constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> patternMatrix, ConstraintValidatorContext context) {
        if (patternMatrix == null || patternMatrix.isEmpty()) {
            return true; // @NotEmpty will handle empty validation
        }

        int length = patternMatrix.get(0).length();
        for (String row : patternMatrix) {
            if (row.length() != length) {
                return false;
            }
        }
        return true;
    }
}

