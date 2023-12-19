package com.nturbo1.telegramBot.service.mapper;

import com.nturbo1.telegramBot.repository.Entity.Category;
import com.nturbo1.telegramBot.service.dto.CategoryDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Set<Category> mapToCategorySet(Set<CategoryDto> categoryDtoSet);
    Set<CategoryDto> mapToCategoryDtoSet(Set<Category> categorySet);
    Category map(CategoryDto categoryDto);
    CategoryDto map(Category category);
}
