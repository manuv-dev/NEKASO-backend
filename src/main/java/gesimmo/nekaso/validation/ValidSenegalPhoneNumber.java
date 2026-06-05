package gesimmo.nekaso.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SenegalPhoneNumberValidatorConstraint.class)
@Documented
public @interface ValidSenegalPhoneNumber {

    String message() default "Numéro de téléphone sénégalais invalide. " +
            "Format attendu : +221XXXXXXXXX, 0XXXXXXXXX, ou XXXXXXXXX. " +
            "Opérateurs supportés : Orange (77, 78), YAS (76), Expresso (70), ProMobile (75).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
