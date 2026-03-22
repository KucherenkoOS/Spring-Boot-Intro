package org.example.springbootintro.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.exception.EntityNotFoundException;
import org.example.springbootintro.mapper.CategoryMapper;
import org.example.springbootintro.model.Category;
import org.example.springbootintro.repository.CategoryRepository;
import org.example.springbootintro.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = getCategoryOrThrow(id);
        return mapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = getCategoryOrThrow(id);
        mapper.updateCategoryFromDto(dto, category);
        return mapper.toDto(repository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private Category getCategoryOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found " + id));
    }
}
