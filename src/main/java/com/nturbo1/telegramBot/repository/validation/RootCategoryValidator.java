package com.nturbo1.telegramBot.repository.validation;

import com.nturbo1.telegramBot.repository.Entity.Category;
import com.nturbo1.telegramBot.repository.annotations.ValidRootCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RootCategoryValidator implements ConstraintValidator<ValidRootCategory, Category> {

    @Override
    public boolean isValid(Category category, ConstraintValidatorContext context) {
        if (category == null) {
            return true; // Let other validators handle null checks
        }

        // Root category should not have a parent
        return !category.getIsRoot() || category.getParent() == null;
    }
}
