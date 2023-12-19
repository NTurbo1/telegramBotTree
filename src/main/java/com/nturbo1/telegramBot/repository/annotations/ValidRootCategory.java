package com.nturbo1.telegramBot.repository.annotations;

import com.nturbo1.telegramBot.repository.validation.RootCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RootCategoryValidator.class})
public @interface ValidRootCategory {
    String message() default "Invalid root category";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
