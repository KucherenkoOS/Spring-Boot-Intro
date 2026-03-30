package org.example.springbootintro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.example.springbootintro.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Find all books by valid category ID")
    @Sql(scripts = {
            "classpath:database/repository/add-books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/repository/delete-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategories_Id_WithValidId_ShouldReturnListOfBooks() {
        // Given
        Long categoryId = 1L;

        // When
        List<Book> actual = bookRepository.findAllByCategories_Id(categoryId);

        // Then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0).getTitle()).isEqualTo("Title 1");
        assertThat(actual.get(0).getAuthor()).isEqualTo("Author 1");
    }

    @Test
    @DisplayName("Return empty list when category ID does not exist")
    @Sql(scripts = {
            "classpath:database/repository/add-books.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/repository/delete-books.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategories_Id_WithNonExistentId_ShouldReturnEmptyList() {
        // Given
        Long nonExistentCategoryId = 999L;

        // When
        List<Book> actual = bookRepository.findAllByCategories_Id(nonExistentCategoryId);

        // Then
        assertThat(actual).isEmpty();
    }
}