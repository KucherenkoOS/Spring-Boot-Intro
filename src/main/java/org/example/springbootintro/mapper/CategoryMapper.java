package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);
}
