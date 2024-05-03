package com.parking.administration.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CPFAndCNPJValidator.class)
public @interface ValidateCPFAndCNPJ {

    public String message() default "Invalid CPF: The CPF entered is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
