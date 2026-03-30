package org.example.springbootintro.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.example.springbootintro.util.TestDataHelper.createBook;
import static org.example.springbootintro.util.TestDataHelper.createBookDto;
import static org.example.springbootintro.util.TestDataHelper.createBookRequestDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.springbootintro.dto.BookDto;
import org.example.springbootintro.dto.BookDtoWithoutCategoryIds;
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
        CreateBookRequestDto requestDto = createBookRequestDto();
        Book book = createBook(null);
        BookDto expectedDto = createBookDto(1L);

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.createBook(requestDto);

        assertThat(actualDto).isEqualTo(expectedDto);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Verify getBookById() returns DTO when book exists")
    void getBookById_WithValidId_ReturnsBookDto() {
        Long bookId = 1L;
        Book book = createBook(bookId);
        BookDto expectedDto = createBookDto(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.getBookById(bookId);

        assertThat(actualDto).isEqualTo(expectedDto);
    }

    @Test
    @DisplayName("Verify getBookById() throws exception when book is missing")
    void getBookById_WithInvalidId_ThrowsException() {
        Long bookId = 100L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> bookService.getBookById(bookId));

        assertThat(exception.getMessage()).isEqualTo("Can't find book by id " + bookId);
    }

    @Test
    @DisplayName("Verify getAll() returns a page of books")
    void getAll_ValidPageable_ReturnsPageOfBookDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Book book = createBook(1L);
        BookDto bookDto = createBookDto(1L);
        Page<Book> bookPage = new PageImpl<>(List.of(book), pageable, 1);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        Page<BookDto> resultPage = bookService.getAll(pageable);

        assertThat(resultPage.getContent()).hasSize(1);
        assertThat(resultPage.getContent().get(0)).isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Verify update() updates book and returns DTO")
    void update_ValidId_ReturnsUpdatedBookDto() {
        Long bookId = 1L;
        CreateBookRequestDto requestDto = createBookRequestDto();
        Book existingBook = createBook(bookId);
        BookDto expectedDto = createBookDto(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);
        when(bookMapper.toDto(existingBook)).thenReturn(expectedDto);

        BookDto actualDto = bookService.update(bookId, requestDto);

        assertThat(actualDto).isEqualTo(expectedDto);
        verify(bookMapper).updateBookFromDto(requestDto, existingBook);
        verify(bookRepository).save(existingBook);
    }

    @Test
    @DisplayName("Verify update() throws exception when book not found")
    void update_InvalidId_ThrowsException() {
        Long bookId = 100L;
        CreateBookRequestDto requestDto = createBookRequestDto();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.update(bookId, requestDto));
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Verify findAllByCategoryId() returns list of books")
    void findAllByCategoryId_ValidId_ReturnsList() {
        Long categoryId = 1L;
        Book book = createBook(1L);
        BookDtoWithoutCategoryIds dtoWithoutCategories = new BookDtoWithoutCategoryIds();
        dtoWithoutCategories.setId(1L);
        dtoWithoutCategories.setTitle(book.getTitle());

        when(bookRepository.findAllByCategories_Id(categoryId)).thenReturn(List.of(book));
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(dtoWithoutCategories);

        List<BookDtoWithoutCategoryIds> result = bookService.findAllByCategoryId(categoryId);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Verify deleteById() calls repository for existing book")
    void deleteById_ExistingId_DeletesBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        bookService.deleteById(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
    @DisplayName("Verify deleteById() throws exception for non-existing book")
    void deleteById_NonExistingId_ThrowsException() {
        Long bookId = 100L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookService.deleteById(bookId));
        verify(bookRepository, never()).deleteById(any());
    }
}
