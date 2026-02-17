package org.example.springbootintro.service;

import java.util.List;
import org.example.springbootintro.model.Book;

public interface BookService {

    Book save(Book book);

    List<Book> findAll();
}
