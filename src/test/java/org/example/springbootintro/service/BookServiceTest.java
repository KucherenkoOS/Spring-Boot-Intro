package org.example.springbootintro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.CreateBookRequestDto;
import org.example.springbootintro.exception.EntityNotFoundException;
import org.example.springbootintro.mapper.BookMapper;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.repository.BookRepository;
import org.example.springbootintro.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify createBook() method works")
    void createBook_ValidRequestDto_ReturnsBookDto() {
        // Given
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test Book");
        requestDto.setAuthor("Author");
        requestDto.setIsbn("123456789");
        requestDto.setPrice(BigDecimal.TEN);

        Book book = new Book();
        book.setTitle(requestDto.getTitle());

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle(book.getTitle());

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        // When
        BookDto actualDto = bookService.createBook(requestDto);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Verify getBookById() returns DTO when book exists")
    void getBookById_WithValidId_ReturnsBookDto() {
        // Given
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        // When
        BookDto actualDto = bookService.getBookById(bookId);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Verify getBookById() throws exception when book is missing")
    void getBookById_WithInvalidId_ThrowsException() {
        // Given
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getBookById(bookId));

        // Then
        String expectedMessage = "Can't find book by id " + bookId;
        assertThat(exception.getMessage()).isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("Verify getAll() returns a page of books")
    void getAll_ValidPageable_ReturnsPageOfBookDtos() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Book book = new Book();
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        BookDto bookDto = new BookDto();

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        // When
        Page<BookDto> resultPage = bookService.getAll(pageable);

        // Then
        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().get(0)).isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Verify update() updates book and returns DTO")
    void update_ValidId_ReturnsUpdatedBookDto() {
        // Given
        Long bookId = 1L;

        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Updated Title");
        requestDto.setAuthor("Updated Author");
        requestDto.setIsbn("111222333");
        requestDto.setPrice(BigDecimal.valueOf(50));

        Book existingBook = new Book();
        existingBook.setId(bookId);

        BookDto expectedDto = new BookDto();
        expectedDto.setId(bookId);
        expectedDto.setTitle("Updated Title");

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);
        when(bookMapper.toDto(existingBook)).thenReturn(expectedDto);

        // When
        BookDto actualDto = bookService.update(bookId, requestDto);

        // Then
        assertThat(actualDto).isEqualTo(expectedDto);

        verify(bookRepository).findById(bookId);
        verify(bookMapper).updateBookFromDto(requestDto, existingBook);
        verify(bookRepository).save(existingBook);
        verify(bookMapper).toDto(existingBook);
    }

    @Test
    @DisplayName("Verify update() throws exception when book not found")
    void update_InvalidId_ThrowsException() {
        // Given
        Long bookId = 100L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.update(bookId, requestDto));

        // Then
        assertThat(exception.getMessage())
                .isEqualTo("Can't find book by id " + bookId);

        verify(bookRepository).findById(bookId);
        verify(bookRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Verify deleteById() throws exception when book does not exist")
    void deleteById_NonExistingId_ThrowsException() {
        // Given
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        // When
        assertThrows(EntityNotFoundException.class, () -> bookService.deleteById(bookId));

        // Then
        verify(bookRepository, times(0)).deleteById(any());
    }

    @Test
    @DisplayName("Verify deleteById() calls repository for existing book")
    void deleteById_ExistingId_DeletesBook() {
        // Given
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        // When
        bookService.deleteById(bookId);

        // Then
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
