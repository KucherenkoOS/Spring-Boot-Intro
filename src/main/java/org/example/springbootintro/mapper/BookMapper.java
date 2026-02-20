package org.example.springbootintro.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(CreateBookRequestDto dto);

    BookDto toDto(Book book);
}
