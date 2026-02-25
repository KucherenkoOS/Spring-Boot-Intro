package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.example.springbootintro.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "id", ignore = true)
    Book toModel(CreateBookRequestDto dto);

    BookDto toDto(Book book);
}
