package org.example.springbootintro.service;

import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto createBook(CreateBookRequestDto bookDto);

    BookDto getBookById(Long id);

    Page<BookDto> getAll(Pageable pageable);

    BookDto update(Long id, CreateBookRequestDto requestDto);

    void deleteById(Long id);
}
