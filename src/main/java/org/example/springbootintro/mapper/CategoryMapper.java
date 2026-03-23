package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto dto);

    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category category);
}
