package org.example.springbootintro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.springbootintro.util.TestDataHelper.createCategory;
import static org.example.springbootintro.util.TestDataHelper.createCategoryDto;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.mapper.CategoryMapper;
import org.example.springbootintro.model.Category;
import org.example.springbootintro.repository.CategoryRepository;
import org.example.springbootintro.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify save() method works")
    void save_ValidCategoryDto_ReturnsCategoryDto() {
        // Given
        CategoryDto requestDto = createCategoryDto(null, "Fantasy");
        Category category = createCategory(null, "Fantasy");
        CategoryDto savedDto = createCategoryDto(1L, "Fantasy");

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(savedDto);

        // When
        CategoryDto actualDto = categoryService.save(requestDto);

        // Then
        assertThat(actualDto).isEqualTo(savedDto);
        verify(categoryRepository).save(category);
    }

    @Test
    @DisplayName("Verify getById() returns DTO when category exists")
    void getById_ValidId_ReturnsCategoryDto() {
        // Given
        Long categoryId = 1L;
        Category category = createCategory(categoryId, "Fiction");
        CategoryDto expectedDto = createCategoryDto(categoryId, "Fiction");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        // When
        CategoryDto actualDto = categoryService.getById(categoryId);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Verify findAll() returns list of categories")
    void findAll_ValidPageable_ReturnsListOfCategoryDtos() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Category category = createCategory(1L, "History");
        CategoryDto categoryDto = createCategoryDto(1L, "History");
        Page<Category> categoryPage = new PageImpl<>(List.of(category));

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // When
        List<CategoryDto> result = categoryService.findAll(pageable);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(categoryDto);
    }

    @Test
    @DisplayName("Verify update() method works")
    void update_ValidIdAndDto_ReturnsUpdatedDto() {
        // Given
        Long categoryId = 1L;
        CategoryDto updateDto = createCategoryDto(null, "Updated Name");
        Category existingCategory = createCategory(categoryId, "Old Name");
        CategoryDto expectedDto = createCategoryDto(categoryId, "Updated Name");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        when(categoryMapper.toDto(existingCategory)).thenReturn(expectedDto);

        // When
        CategoryDto result = categoryService.update(categoryId, updateDto);

        // Then
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(categoryMapper).updateCategoryFromDto(updateDto, existingCategory);
    }
}
