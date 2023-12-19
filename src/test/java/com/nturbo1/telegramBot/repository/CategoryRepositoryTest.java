package com.nturbo1.telegramBot.repository;

import com.nturbo1.telegramBot.TelegramBotApplication;
import com.nturbo1.telegramBot.repository.Entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(excludeAutoConfiguration = {TelegramBotApplication.class})
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @Test
    void rootDoesNotExistTest() {
        // When
        Category foundRoot = categoryRepository.findTheRoot();

        // Then
        assertNull(foundRoot);
    }

    @Test
    void rootExistsTest() {
        // Given
        Category rootCategory = new Category();
        rootCategory.setName("RootCategory");
        rootCategory.setIsRoot(true);
        categoryRepository.save(rootCategory);

        // When
        Category foundRoot = categoryRepository.findTheRoot();

        // Then
        assertTrue(foundRoot.getIsRoot());
        assertEquals("RootCategory", foundRoot.getName());
    }
}