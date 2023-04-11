package io.github.juarez.VendasApplication.validation.constraintValidation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import io.github.juarez.VendasApplication.validation.NotEmptyList;

public class NotEmptyListValidation
 implements ConstraintValidator<NotEmptyList, List>{

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list != null && !list.isEmpty();
    }

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        
    }

    
    
}
