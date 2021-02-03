package com.florindanciu.opherUpbackend.item.dto;

import com.florindanciu.opherUpbackend.item.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDto modelToDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> modelToDto(List<Category> categories) {
        return categories.stream().map(this::modelToDto).collect(Collectors.toList());
    }

    public Category dtoToModel(CategoryDto categoryDto) {
        return modelMapper.map(categoryDto, Category.class);
    }

    public List<Category> dtoToModel(List<CategoryDto> categoryDtoList) {
        return categoryDtoList.stream().map(this::dtoToModel).collect(Collectors.toList());
    }
}
