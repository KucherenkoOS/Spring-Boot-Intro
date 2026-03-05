package org.example.springbootintro.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.example.springbootintro.exception.DataProcessingException;
import org.example.springbootintro.model.Book;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Book save(Book book) {
        try {
            entityManager.persist(book);
            return book;
        } catch (Exception e) {
            throw new DataProcessingException("Can't save book: " + book, e);
        }
    }

    @Override
    public List<Book> findAll() {
        try {
            return entityManager.createQuery("SELECT b FROM Book b", Book.class)
                    .getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all books from DB", e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            Book book = entityManager.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            throw new DataProcessingException("Can't find book by id: " + id, e);
        }
    }
}
