package em.common;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValid, String > {

    private EnumValid enumValid;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)){
            return false;
        }

        Object[] enumValues = this.enumValid.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (this.enumValid.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
