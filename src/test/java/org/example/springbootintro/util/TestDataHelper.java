package org.example.springbootintro.util;

import java.math.BigDecimal;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CategoryDto;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.model.Category;

public class TestDataHelper {

    public static CreateBookRequestDto createBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test Book");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("123456789");
        requestDto.setPrice(BigDecimal.TEN);
        return requestDto;
    }

    public static Book createBook(Long id) {
        Book book = new Book();
        book.setId(id);
        book.setTitle("Test Book");
        book.setAuthor("Author");
        book.setIsbn("123456789");
        book.setPrice(BigDecimal.TEN);
        return book;
    }

    public static BookDto createBookDto(Long id) {
        BookDto bookDto = new BookDto();
        bookDto.setId(id);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("Author");
        bookDto.setPrice(BigDecimal.TEN);
        return bookDto;
    }

    public static Category createCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription("Description for " + name);
        return category;
    }

    public static CategoryDto createCategoryDto(Long id, String name) {
        CategoryDto dto = new CategoryDto();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription("Description for " + name);
        return dto;
    }

    public static CreateBookRequestDto createBookRequest(String title, String isbn) {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle(title);
        requestDto.setAuthor("Some Author");
        requestDto.setIsbn(isbn);
        requestDto.setPrice(BigDecimal.valueOf(45.99));
        return requestDto;
    }

    public static CategoryDto createCategoryRequest(String name) {
        CategoryDto dto = new CategoryDto();
        dto.setName(name);
        dto.setDescription("Description for " + name);
        return dto;
    }
}
