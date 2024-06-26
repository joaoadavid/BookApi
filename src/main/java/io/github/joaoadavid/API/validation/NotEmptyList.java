package io.github.joaoadavid.API.validation;

import io.github.joaoadavid.API.validation.constraintvalidation.NotEmptyListValidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//significa a verficação em tempo real
@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";
    Class<?>[] groups() default { };// retirado dentro da anottation NotNull
    //são métodos de validação
    Class<? extends Payload>[] payload() default { };
}
