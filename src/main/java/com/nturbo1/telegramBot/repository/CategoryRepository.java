package com.nturbo1.telegramBot.repository;

import com.nturbo1.telegramBot.repository.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
    @Query("select c from Category c where c.isRoot = TRUE")
    Category findTheRoot();
}
