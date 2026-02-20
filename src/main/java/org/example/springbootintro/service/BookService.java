package org.example.springbootintro.service;

import java.util.List;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;

public interface BookService {

    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto getBookById(Long id);

    List<BookDto> getAll();
}
