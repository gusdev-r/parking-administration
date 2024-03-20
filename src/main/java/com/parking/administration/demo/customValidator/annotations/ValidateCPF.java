package com.parking.administration.demo.customValidator.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CpfValidator.class)
public @interface ValidateCPF {

    public String message() default "Invalid CPF: The CPF entered is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
