package io.github.joaoadavid.vendasv1.validation.constraintvalidation;

import io.github.joaoadavid.vendasv1.validation.NotEmptyList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.List;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List> {

    @Override
    public boolean isValid(List list, ConstraintValidatorContext constraintValidatorContext) {
        return list !=null && !list.isEmpty();
    }
    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
    }


}
