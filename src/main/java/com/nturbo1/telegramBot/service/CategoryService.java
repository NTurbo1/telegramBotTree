package com.nturbo1.telegramBot.service;

import com.nturbo1.telegramBot.repository.CategoryRepository;
import com.nturbo1.telegramBot.repository.Entity.Category;
import com.nturbo1.telegramBot.service.dto.CategoryDto;
import com.nturbo1.telegramBot.service.exceptions.*;
import com.nturbo1.telegramBot.service.mapper.CategoryMapper;
import com.nturbo1.telegramBot.service.validation.CategoryValidator;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Implements the logic for all the commands
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;

    /**
     *
     * @return tree structure of the current categories
     */
    public String printTree() {
        Category root = categoryRepository.findTheRoot();
        if (root != null) {
            StringBuilder tree = new StringBuilder();
            printTreeRecursive(tree, root, 0);
            return tree.toString();
        } else {
            return "No root category found.";
        }
    }

    /**
     * Since the parent element is not specified, the element will be added as the root.
     * @param elementName - root element's name
     * @return Successfully added category's data
     * @throws ElementAlreadyExistsException If a Category with the element name already exists.
     */
    @Transactional
    public CategoryDto addElement(String elementName) throws ElementAlreadyExistsException {

        categoryValidator.validateIfElementAlreadyExists(elementName);

        Category newCategory = new Category();
        newCategory.setName(elementName);
        newCategory.setIsRoot(true);

        Category savedCategory = saveNewRootCategory(newCategory);

        return categoryMapper.map(savedCategory);
    }

    /**
     * Adds the element as a child of the parent element
     * @param parentName - name of the element's parent
     * @param elementName - element's name
     * @return Successfully added category's data
     * @throws ParentNotFoundException If a Category with the parent name doesn't exist.
     * @throws ElementAlreadyExistsException If a Category with the element name already exists.
     */
    @Transactional
    public CategoryDto addElement(String parentName, String elementName)
            throws ParentNotFoundException, ElementAlreadyExistsException {

        Category parentCategory = categoryValidator.validateIfParentElementExists(parentName);
        categoryValidator.validateIfElementAlreadyExists(elementName);

        Category newCategory = new Category();
        newCategory.setName(elementName);

        newCategory.setParent(parentCategory);

        // Ensure the parent is in the managed state
        if (parentCategory.getId() == null) {
            categoryRepository.save(parentCategory);
        }

        parentCategory.getChildren().add(newCategory);

        categoryRepository.save(parentCategory);
        Category savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.map(savedCategory);
    }

    /**
     *
     * @param elementName - name of the element to be deleted.
     * @throws ElementNotFoundException - ElementNotFound is thrown if a Category with the given
     * element name doesn't exist.
     */
    @Transactional
    public void removeElement(String elementName) throws ElementNotFoundException {
        Category existingCategory = categoryValidator.validateIfElementDoesNotExist(elementName);

        Category parent = existingCategory.getParent();
        if (parent != null) {
            parent.getChildren().remove(existingCategory);
            categoryRepository.save(parent);
        }

        categoryRepository.delete(existingCategory);
    }

    /**
     *
     * @return A string of a list of all the available commands and their description
     */
    public String help() {

        return """
                Available Commands:
                /viewTree - View the tree structure
                /addElement <elementName> - Add a root element
                /addElement <parentName> <elementName> - Add a child element
                /removeElement <elementName> - Remove an element
                /download - download an Excel file with tree structure data inside
                /upload - receives an Excel file with tree structure data and saves it
                /help - Display available commands and their descriptions
                """;
    }

    /**
     * Implements the /download command logic.
     * Download tree structure as an Excel document.
     *
     * If a Category doesn't have a parent id, its parent id is set to -1
     */
    public File downloadTreeAsExcel() throws IOException {
        List<Category> categories = categoryRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Categories");

            // Create headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Is Root");
            headerRow.createCell(3).setCellValue("Parent ID");

            // Populate data
            int rowNum = 1;
            for (Category category : categories) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(category.getId());
                row.createCell(1).setCellValue(category.getName());
                row.createCell(2).setCellValue(category.getIsRoot());
                row.createCell(3)
                        .setCellValue(
                                category.getParent() != null ? category.getParent().getId() : -1);
            }

            // Save the workbook to a file
            File excelFile = File.createTempFile("categories", ".xlsx");
            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                workbook.write(fileOut);
            }

            return excelFile;
        }
    }

    /**
     * Implements the /upload command logic.
     * Upload tree structure from an Excel document.
     */
    public void uploadTreeFromExcel(File excelFile) throws IOException,
            RootHasParentException, ElementAlreadyExistsException, ParentNotFoundException,
            InvalidDataType, NonRootElementHasNoParentException {

        try (Workbook workbook = WorkbookFactory.create(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip header row
                    continue;
                }

                // Extract category data from the row to a Category object
                Category category = createCategoryFromRow(row);

                // Validate the extracted category object if it can be stored in the database
                // throws multiple Exceptions
                categoryValidator.validateIfUploadedCategoryCanBeSaved(category);

                if (category.getIsRoot())
                    saveNewRootCategory(category);
                else
                    categoryRepository.save(category);

                Category parent = category.getParent();
                if (parent != null) {
                    categoryRepository.save(parent);
                }
            }
        }
    }

    private Category createCategoryFromRow(Row row) throws InvalidDataType {

        long id;
        try {
            id = (long) row.getCell(0).getNumericCellValue();
        } catch (Exception e) {
            throw new InvalidDataType("ID column must be numerical data type!");
        }

        String name = row.getCell(1).getStringCellValue();
        boolean isRoot = false;
        Category parent = null;

        // Extract the isRoot field data
        // throws InvalidDataType if the data type is NOT boolean or numerical.
        Cell isRootCell = row.getCell(2);
        if (isRootCell.getCellType() == CellType.BOOLEAN)
            isRoot = isRootCell.getBooleanCellValue();
        else if (isRootCell.getCellType() == CellType.NUMERIC) {
            isRoot = (isRootCell.getNumericCellValue() > 0);
        } else {
            throw new InvalidDataType("\"is Root\" column must be either boolean or numerical!");
        }

        // Extract the parent data
        Cell parentCell = row.getCell(3);
        if (
                parentCell != null &&
                        parentCell.getCellType() == CellType.NUMERIC &&
                        parentCell.getNumericCellValue() > 0
        ) {
            Long parentId = (long) parentCell.getNumericCellValue();
            parent = categoryRepository.findById(parentId).orElse(null);
        }

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setIsRoot(isRoot);
        category.setParent(parent);


        if (parent != null) {
            parent.getChildren().add(category);
        }

        return category;
    }

    private Category saveNewRootCategory(Category newCategory) {
        Category root = categoryRepository.findTheRoot();

        if (root != null) {
            root.setIsRoot(false);
            root.setParent(newCategory);

            newCategory.getChildren().add(root);
        }

        Category savedCategory = categoryRepository.save(newCategory);
        if (root != null) {
            categoryRepository.save(root);
        }
        return savedCategory;
    }

    private void printTreeRecursive(StringBuilder tree, Category category, int level) {
        tree.append("     ".repeat(Math.max(0, level)));
        tree.append("- ").append(category.getName()).append("\n");

        for (Category child : category.getChildren()) {
            printTreeRecursive(tree, child, level + 1);
        }
    }
}
