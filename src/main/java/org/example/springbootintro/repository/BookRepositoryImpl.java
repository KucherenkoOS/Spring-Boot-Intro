package org.example.springbootintro.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.exception.EntityNotFoundException;
import org.example.springbootintro.model.Book;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery("from Book", Book.class).getResultList();
        }
    }

    @Override
    public Book findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            Book book = entityManager.find(Book.class, id);
            if (book == null) {
                throw new EntityNotFoundException("Book with id " + id + " not found");
            }
            return book;
        } finally {
            entityManager.close();
        }
    }
}
