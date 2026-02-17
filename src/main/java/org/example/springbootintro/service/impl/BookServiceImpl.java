package org.example.springbootintro.service.impl;

import java.util.List;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.repository.BookRepository;
import org.example.springbootintro.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
