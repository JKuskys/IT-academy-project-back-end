package com.project.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD } )
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Slaptažodis turi turėti bent vieną didžiąją raidę, mažąją raidę ir skaičių ir būti sudarytas iš bent 7 simbolių";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
