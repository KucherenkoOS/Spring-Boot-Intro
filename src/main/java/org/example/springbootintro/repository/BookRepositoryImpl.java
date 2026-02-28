package org.example.springbootintro.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.springbootintro.model.Book;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BookRepositoryImpl implements BookRepository {

    private final EntityManagerFactory entityManagerFactory;

    @Override
    public Book save(Book book) {
        EntityTransaction transaction = null;

        try (EntityManager entityManager =
                     entityManagerFactory.createEntityManager()) {

            transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(book);

            transaction.commit();
            return book;

        } catch (RuntimeException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't save book: " + book, e
            );
        }
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager =
                     entityManagerFactory.createEntityManager()) {

            return entityManager
                    .createQuery("from Book", Book.class)
                    .getResultList();
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (EntityManager entityManager =
                     entityManagerFactory.createEntityManager()) {

            Book book = entityManager.find(Book.class, id);
            return Optional.ofNullable(book);
        }
    }
}
