package org.example.springbootintro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.BookDtoWithoutCategoryIds;
import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.mapper.BookMapper;
import org.example.springbootintro.repository.BookRepository;
import org.example.springbootintro.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category management", description = "Endpoints for managing categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new book category")
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto dto) {
        return categoryService.save(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get all categories")
    public List<CategoryDto> getAll(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get category by id")
    public CategoryDto getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update category by id")
    public CategoryDto update(@PathVariable Long id,
                              @RequestBody CategoryDto dto) {
        return categoryService.update(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by id (Soft deleting)")
    public void delete(@PathVariable Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}/books")
    @Operation(summary = "Get all books from category id")
    public List<BookDtoWithoutCategoryIds> getBooksByCategory(@PathVariable Long id) {
        return bookRepository.findAllByCategories_Id(id)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
