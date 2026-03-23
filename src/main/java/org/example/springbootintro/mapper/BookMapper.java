package org.example.springbootintro.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.BookDtoWithoutCategoryIds;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Book toModel(CreateBookRequestDto dto);

    BookDto toDto(Book book);

    void updateBookFromDto(CreateBookRequestDto dto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto dto, Book book) {
        Set<Long> ids = book.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        dto.setCategoryIds(ids);
    }
}
