package com.nturbo1.telegramBot.service.validation;

import com.nturbo1.telegramBot.repository.CategoryRepository;
import com.nturbo1.telegramBot.repository.Entity.Category;
import com.nturbo1.telegramBot.service.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryValidator {

    private final CategoryRepository categoryRepository;

    public Category validateIfElementAlreadyExists(String elementName)
            throws ElementAlreadyExistsException {
        Category existingCategory = categoryRepository.findByName(elementName);

        if (existingCategory != null) {
            throw new ElementAlreadyExistsException(
                    "Element with name " + elementName + " already exists.");
        }

        return existingCategory;
    }

    public Category validateIfElementAlreadyExists(Long elementId)
            throws ElementAlreadyExistsException {
        Optional<Category> existingCategory = categoryRepository.findById(elementId);

        if (existingCategory.isPresent()) {
            throw new ElementAlreadyExistsException(
                    "Element with id " + elementId + " already exists.");
        }

        return null;
    }

    public Category validateIfParentElementExists(String parentElementName)
            throws ParentNotFoundException {
        Category parentCategory = categoryRepository.findByName(parentElementName);

        if (parentCategory == null) {
            throw new ParentNotFoundException(
                    "Parent category with name " + parentElementName + " is not found.");
        }

        return parentCategory;
    }

    public Category validateIfElementDoesNotExist(String elementName)
            throws ElementNotFoundException {
        Category existingCategory = categoryRepository.findByName(elementName);

        if (existingCategory == null) {
            throw new ElementNotFoundException(
                    "Category with name " + elementName + " is not found.");
        }

        return existingCategory;
    }

    public Category validateIfUploadedCategoryCanBeSaved(Category category)
            throws ElementAlreadyExistsException, ParentNotFoundException,
            RootHasParentException, NonRootElementHasNoParentException {

        validateIfElementAlreadyExists(category.getId());
        validateIfElementAlreadyExists(category.getName());

        Category parent = category.getParent();

        if (parent != null && category.getIsRoot()) {
            throw new RootHasParentException("Root category can not have a parent.");
        }

        if (parent != null) {
            validateIfParentElementExists(parent.getName());
        }

        if (parent == null && !category.getIsRoot()) {
            throw new NonRootElementHasNoParentException(
                    "A non-root element MUST have a parent.");
        }

        return category;
    }
}
