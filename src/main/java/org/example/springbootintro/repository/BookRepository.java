package org.example.springbootintro.repository;

import java.util.List;
import org.example.springbootintro.model.Book;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();
}
