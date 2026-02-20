package org.example.springbootintro.mapper;

import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.example.springbootintro.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toModel(CreateBookRequestDto dto);

    BookDto toDto(Book book);
}
