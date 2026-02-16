package org.example.springbootintro;

import java.math.BigDecimal;
import org.example.springbootintro.model.Book;
import org.example.springbootintro.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootIntroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootIntroApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(BookService bookService) {
        return args -> {
            Book book = new Book();
            book.setTitle("Clean Code");
            book.setAuthor("Robert Martin");
            book.setIsbn("1234567890");
            book.setPrice(BigDecimal.valueOf(29.99));
            book.setDescription("Best practices for writing clean code");
            book.setCoverImage("cover.jpg");

            bookService.save(book);

            bookService.findAll().forEach(System.out::println);
        };
    }

}
