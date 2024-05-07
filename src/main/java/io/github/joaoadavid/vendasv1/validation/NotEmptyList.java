package io.github.joaoadavid.vendasv1.validation;

import io.github.joaoadavid.vendasv1.validation.constraintvalidation.NotEmptyListValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

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
