package gesimmo.nekaso.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SenegalPhoneNumberValidatorConstraint implements ConstraintValidator<ValidSenegalPhoneNumber, String> {

    @Autowired
    private SenegalPhoneNumberValidator validator;

    @Override
    public void initialize(ValidSenegalPhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return true;
        }

        if (validator == null) {
            validator = new SenegalPhoneNumberValidator();
        }

        return validator.isValid(phoneNumber);
    }
}
