package com.nturbo1.telegramBot.service.dto;

import com.nturbo1.telegramBot.repository.Entity.Category;

import java.util.List;
import java.util.Set;

public record CategoryDto(
        String name,
        Category parent,
        Set<Category> children
) {

}
