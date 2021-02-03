package com.florindanciu.opherUpbackend.item.service;

import com.florindanciu.opherUpbackend.item.dto.CategoryConverter;
import com.florindanciu.opherUpbackend.item.dto.CategoryDto;
import com.florindanciu.opherUpbackend.item.model.Category;
import com.florindanciu.opherUpbackend.item.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter converter;

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return converter.modelToDto(categories);
    }

    public CategoryDto getCategoryById(UUID id) {
        return converter.modelToDto(categoryRepository.getOne(id));
    }
}
