package org.example.springbootintro.service;

import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;

import java.util.List;

public interface BookService {

    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto getBookById(Long id);

    List<BookDto> getAll();
}
