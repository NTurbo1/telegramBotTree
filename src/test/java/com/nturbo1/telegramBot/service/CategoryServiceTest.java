package com.nturbo1.telegramBot.service;

import com.nturbo1.telegramBot.repository.CategoryRepository;
import com.nturbo1.telegramBot.repository.Entity.Category;
import com.nturbo1.telegramBot.service.dto.CategoryDto;
import com.nturbo1.telegramBot.service.exceptions.ElementAlreadyExistsException;
import com.nturbo1.telegramBot.service.exceptions.ElementNotFoundException;
import com.nturbo1.telegramBot.service.exceptions.ParentNotFoundException;
import com.nturbo1.telegramBot.service.mapper.CategoryMapper;
import com.nturbo1.telegramBot.service.utils.ExcelComparator;
import com.nturbo1.telegramBot.service.validation.CategoryValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryValidator categoryValidator;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository, categoryMapper, categoryValidator);
    }

    @AfterEach
    void tearDown() throws Exception {
        categoryRepository.deleteAll();
    }


    // addElement tests
    @Test
    void addElementShouldAddRootElementSuccessfully() throws ElementAlreadyExistsException {
        // Arrange
        String elementName = "NewRoot";

        when(categoryRepository.findByName(elementName)).thenReturn(null);
        when(categoryRepository.findTheRoot()).thenReturn(null);

        Category savedCategory = new Category();
        savedCategory.setName(elementName);
        savedCategory.setIsRoot(true);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDto result = categoryService.addElement(elementName);

        // Assert
        assertNotNull(result);
        assertEquals(elementName, result.name());
    }

    @Test
    void addElementWithParentShouldAddChildElementSuccessfully()
            throws ParentNotFoundException, ElementAlreadyExistsException {
        // Arrange
        String parentName = "Parent";
        String elementName = "NewChild";

        Category parentCategory = new Category();
        parentCategory.setId(1L);
        parentCategory.setName(parentName);
        parentCategory.setIsRoot(true);

        when(categoryValidator.validateIfParentElementExists(parentName))
                .thenReturn(parentCategory);
        when(categoryValidator.validateIfElementAlreadyExists(elementName))
                .thenReturn(null);

        Category savedCategory = new Category();
        savedCategory.setId(2L);
        savedCategory.setName(elementName);
        savedCategory.setParent(parentCategory);

        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        // Act
        CategoryDto result = categoryService.addElement(parentName, elementName);

        // Assert
        assertNotNull(result);
        assertEquals(elementName, result.name());
        assertNotNull(result.parent());
        assertEquals(parentName, result.parent().getName());
    }

    @Test
    void addElementShouldThrowExceptionIfElementAlreadyExists()
            throws ElementAlreadyExistsException {
        // Arrange
        String elementName = "ExistingElement";

        when(categoryRepository.findByName(elementName)).thenReturn(new Category());
        when(categoryValidator.validateIfElementAlreadyExists(elementName))
                .thenThrow(new ElementAlreadyExistsException(
                        "Element with name " + elementName + " already exists."));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> {
            categoryService.addElement(elementName);
        });
    }

    @Test
    void addElementWithParentShouldThrowExceptionIfParentNotFound()
            throws ParentNotFoundException {
        // Arrange
        String parentName = "NonExistentParent";
        String elementName = "NewChild";

        when(categoryValidator.validateIfParentElementExists(parentName))
                .thenThrow(new ParentNotFoundException(
                        "Parent category with name " + parentName + " is not found."
                ));

        // Act & Assert
        assertThrows(ParentNotFoundException.class, () -> {
            categoryService.addElement(parentName, elementName);
        });
    }

    @Test
    void addElementWithParentShouldThrowExceptionIfElementAlreadyExists()
            throws ParentNotFoundException, ElementAlreadyExistsException {
        // Arrange
        String parentName = "Parent";
        String elementName = "ExistingChild";

        Category parentCategory = new Category();
        parentCategory.setId(1L);
        parentCategory.setName(parentName);
        parentCategory.setIsRoot(true);

        when(categoryValidator.validateIfParentElementExists(parentName))
                .thenReturn(parentCategory);
        when(categoryValidator.validateIfElementAlreadyExists(elementName))
                .thenThrow(new ElementAlreadyExistsException(
                        "Element with name " + elementName + " already exists."));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> {
            categoryService.addElement(parentName, elementName);
        });
    }

    // printTree tests
    @Test
    void printTreeShouldReturnTreeString() {
        // Given
        Category root = setupCategories().get(0);

        // When
        when(categoryRepository.findTheRoot()).thenReturn(root);

        // Then
        String result = categoryService.printTree();

        assertNotNull(result);
        assertEquals("- Root\n" +
                "     - cat1\n" +
                "          - cat3\n" +
                "               - cat4\n" +
                "               - cat5\n" +
                "     - cat2\n" +
                "          - cat6\n", result);
    }

    @Test
    void printTreeShouldReturnErrorMessageIfNoRoot() {
        // Arrange
        when(categoryRepository.findTheRoot()).thenReturn(null);

        // Act
        String result = categoryService.printTree();

        // Assert
        assertNotNull(result);
        assertEquals("No root category found.", result);
    }

    // removeElement tests
    @Test
    void removeElementShouldRemoveElementSuccessfully() throws ElementNotFoundException {
        // Arrange
        String elementName = "ElementToRemove";

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName(elementName);

        when(categoryValidator.validateIfElementDoesNotExist(elementName))
                .thenReturn(existingCategory);

        // Act
        assertDoesNotThrow(() -> {
            categoryService.removeElement(elementName);
        });

        // Assert
        verify(categoryRepository, times(1)).delete(existingCategory);
    }

    @Test
    void removeElementShouldThrowExceptionIfElementNotFound() throws ElementNotFoundException {
        // Arrange
        String elementName = "NonExistentElement";

        when(categoryValidator.validateIfElementDoesNotExist(elementName))
                .thenThrow(new ElementNotFoundException(
                        "Category with name " + elementName + " is not found."));

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> {
            categoryService.removeElement(elementName);
        });
    }

    // help tests
    @Test
    void helpShouldReturnCommandDescriptions() {
        // Act
        String result = categoryService.help();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("/viewTree"));
        assertTrue(result.contains("/addElement <elementName>"));
        assertTrue(result.contains("/addElement <parentName> <elementName>"));
        assertTrue(result.contains("/removeElement <elementName>"));
        assertTrue(result.contains("/download"));
        assertTrue(result.contains("/upload"));
        assertTrue(result.contains("/help"));
    }

    // downloadTreeAsExcel tests
    @Test
    void downloadTreeAsExcelShouldCreateExcelFile() throws Exception {
        // Arrange
        List<Category> categories = new ArrayList<>();
        Category root = new Category();
        root.setId(1L);
        root.setName("Root");
        root.setIsRoot(true);
        categories.add(root);

        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        File result = categoryService.downloadTreeAsExcel();

        // Assert
        assertNotNull(result);
        assertTrue(result.exists());
        assertTrue(result.isFile());
    }

    @Test
    void downloadTreeAsExcelShouldCreateExcelFileWithCorrectContent() throws Exception {
        // Arrange
        List<Category> categories = setupCategories();
        File testExcelFile = new File("src/test/java/com/nturbo1/telegramBot/service/files/downloadCommandTestExcelFile.xlsx");

        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        File result = categoryService.downloadTreeAsExcel();

        // Assert
        assertNotNull(result);
        assertTrue(result.exists());
        assertTrue(result.isFile());
        assertTrue(ExcelComparator.compareExcelFiles(testExcelFile, result));
    }

    @Test
    void uploadTreeFromExcelShouldUploadData() throws Exception {
        // Arrange
        File excelFile = new File("src/test/java/com/nturbo1/telegramBot/service/files/testSuccessfulExcelFileUpload.xlsx");

        List<Category> categories = new ArrayList<>();
        Category root = new Category();
        root.setId(1L);
        root.setName("Root");
        root.setIsRoot(true);
        categories.add(root);

        when(categoryRepository.findById(anyLong())).thenReturn(java.util.Optional.of(root));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());
        when(categoryValidator.validateIfUploadedCategoryCanBeSaved(any(Category.class)))
                .thenReturn(new Category());

        // Act
        assertDoesNotThrow(() -> {
            categoryService.uploadTreeFromExcel(excelFile);
        });

        // Assert
        verify(categoryRepository, times(6)).save(any(Category.class));
    }


    // the first element is the root
    private static List<Category> setupCategories() {
        Category root = new Category();
        root.setId(1L);
        root.setName("Root");
        root.setIsRoot(true);

        Category cat1 = new Category();
        cat1.setId(2L);
        cat1.setIsRoot(false);
        cat1.setName("cat1");
        cat1.setParent(root);

        Category cat2 = new Category();
        cat2.setId(3L);
        cat2.setIsRoot(false);
        cat2.setName("cat2");
        cat2.setParent(root);

        Category cat3 = new Category();
        cat3.setId(4L);
        cat3.setIsRoot(false);
        cat3.setName("cat3");
        cat3.setParent(cat1);

        Category cat4 = new Category();
        cat4.setId(5L);
        cat4.setIsRoot(false);
        cat4.setName("cat4");
        cat4.setParent(cat3);

        Category cat5 = new Category();
        cat5.setId(2L);
        cat5.setIsRoot(false);
        cat5.setName("cat5");
        cat5.setParent(cat3);

        Category cat6 = new Category();
        cat6.setId(2L);
        cat6.setIsRoot(false);
        cat6.setName("cat6");
        cat6.setParent(cat2);

        // Set childrens
        Set<Category> rootChildren = new LinkedHashSet<>();
        rootChildren.add(cat1);
        rootChildren.add(cat2);
        root.setChildren(rootChildren);

        Set<Category> cat1Children = new LinkedHashSet<>();
        cat1Children.add(cat3);
        cat1.setChildren(cat1Children);

        Set<Category> cat2Children = new LinkedHashSet<>();
        cat2Children.add(cat6);
        cat2.setChildren(cat2Children);

        Set<Category> cat3Children = new LinkedHashSet<>();
        cat3Children.add(cat4);
        cat3Children.add(cat5);
        cat3.setChildren(cat3Children);

        cat4.setChildren(new LinkedHashSet<>());
        cat5.setChildren(new LinkedHashSet<>());
        cat6.setChildren(new LinkedHashSet<>());

        List<Category> categories = new ArrayList<>();
        categories.add(root);
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        categories.add(cat4);
        categories.add(cat5);
        categories.add(cat6);

        return categories;
    }
}