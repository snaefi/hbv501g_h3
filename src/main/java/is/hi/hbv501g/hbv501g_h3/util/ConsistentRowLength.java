package is.hi.hbv501g.hbv501g_h3.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ConsistentRowLengthValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentRowLength {
    String message() default "All rows in the pattern matrix must have the same length";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

