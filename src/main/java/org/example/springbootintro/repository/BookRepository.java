package org.example.springbootintro.repository;

import java.util.List;
import org.example.springbootintro.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByCategories_Id(Long categoryId);
}
