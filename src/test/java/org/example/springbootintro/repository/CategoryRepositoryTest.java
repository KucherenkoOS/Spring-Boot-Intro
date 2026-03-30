package org.example.springbootintro.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.example.springbootintro.model.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find category by valid ID from the provided dataset")
    @Sql(scripts = "classpath:database/categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_WithValidId_ShouldReturnFantasyCategory() {
        // Given
        Long fantasyId = 1L;

        // When
        Optional<Category> actual = categoryRepository.findById(fantasyId);

        // Then
        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("Fantasy");
        assertThat(actual.get().getDescription()).isEqualTo("Books about magic");
    }

    @Test
    @DisplayName("Find all categories should return both Fantasy and Sci-Fi")
    @Sql(scripts = "classpath:database/categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll_ShouldReturnAllCategoriesFromSql() {
        // When
        List<Category> actual = categoryRepository.findAll();

        // Then
        assertThat(actual).hasSize(2);
        assertThat(actual).extracting(Category::getName)
                .containsExactlyInAnyOrder("Fantasy", "Sci-Fi");
    }

    @Test
    @DisplayName("Check if category remains after deletion of the link in book_category")
    @Sql(scripts = "classpath:database/categories/add-categories.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/categories/remove-categories.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById_CategoryWithBook_ShouldStillBeAccessible() {
        Optional<Category> actual = categoryRepository.findById(1L);

        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("Fantasy");
    }
}
