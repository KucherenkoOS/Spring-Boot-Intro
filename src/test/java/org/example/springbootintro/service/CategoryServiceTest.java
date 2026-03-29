package org.example.springbootintro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.exception.EntityNotFoundException;
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
        CategoryDto requestDto = new CategoryDto();
        requestDto.setName("Fantasy");
        requestDto.setDescription("Magic and dragons");

        Category category = new Category();
        category.setName(requestDto.getName());

        CategoryDto savedDto = new CategoryDto();
        savedDto.setId(1L);
        savedDto.setName(category.getName());

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(savedDto);

        // When
        CategoryDto actualDto = categoryService.save(requestDto);

        // Then
        assertThat(actualDto).isEqualTo(savedDto);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Verify getById() returns DTO when category exists")
    void getById_ValidId_ReturnsCategoryDto() {
        // Given
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Fiction");

        CategoryDto expectedDto = new CategoryDto();
        expectedDto.setId(categoryId);
        expectedDto.setName("Fiction");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expectedDto);

        // When
        CategoryDto actualDto = categoryService.getById(categoryId);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Verify getById() throws exception when category not found")
    void getById_InvalidId_ThrowsException() {
        // Given
        Long categoryId = 100L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> categoryService.getById(categoryId)
        );

        // Then
        assertThat(exception.getMessage()).isEqualTo("Category not found " + categoryId);
    }

    @Test
    @DisplayName("Verify findAll() returns list of categories")
    void findAll_ValidPageable_ReturnsListOfCategoryDtos() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category();
        category.setName("History");

        Page<Category> categoryPage = new PageImpl<>(List.of(category));
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("History");

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);
        when(categoryMapper.toDto(category)).thenReturn(categoryDto);

        // When
        List<CategoryDto> result = categoryService.findAll(pageable);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("History");
    }

    @Test
    @DisplayName("Verify update() method works")
    void update_ValidIdAndDto_ReturnsUpdatedDto() {
        // Given
        Long categoryId = 1L;
        CategoryDto updateDto = new CategoryDto();
        updateDto.setName("Updated Name");

        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Old Name");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
        when(categoryMapper.toDto(existingCategory)).thenReturn(updateDto);

        // When
        CategoryDto result = categoryService.update(categoryId, updateDto);

        // Then
        assertThat(result.getName()).isEqualTo("Updated Name");
        verify(categoryMapper).updateCategoryFromDto(updateDto, existingCategory);
    }
}
